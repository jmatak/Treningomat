package com.errorsonogsvijeta.treningomat.scheduler;

import com.errorsonogsvijeta.treningomat.model.Event;
import com.errorsonogsvijeta.treningomat.model.administration.Subscription;
import com.errorsonogsvijeta.treningomat.repository.EventRepository;
import com.errorsonogsvijeta.treningomat.repository.SubscriptionRepository;
import com.errorsonogsvijeta.treningomat.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

@Component
public class ReceiptScheduler {
    private static final String LAST_GENERATED = "last_generated";
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private EventRepository eventRepository;

    private static final Logger log = LoggerFactory.getLogger(ReceiptScheduler.class);

    // https://stackoverflow.com/questions/26147044/spring-cron-expression-for-every-day-101am
    @Scheduled(cron = "0 2 20 * * ?")
    private void updateReceipts() {
        Date lastRun = new Date(eventRepository.findByName(LAST_GENERATED).getDate().getTime());
        if (DAYS.between(convertDate(lastRun), getYesterday()) == 0) {
            log.info("receipts already generated for " + getYesterday().toString());
            return;
        }

        long ms1 = new Date().getTime();
        log.info("receipt generation started");
        int count = generateReceipts();
        long ms2 = new Date().getTime();
        log.info(count + " receipts generated in " + (ms2 - ms1) + "ms");
    }

    private int generateReceipts() {
        LocalDate yesterday = getYesterday();
        Event event = eventRepository.findByName(LAST_GENERATED);
        event.setDate(convertDate(yesterday));
        eventRepository.save(event);

        return generateReceiptsFor(yesterday);
    }

    public int generateReceiptsFor(LocalDate localDate) {
        int n = 0;

        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriptionEndNull();
        for (Subscription subscription : subscriptions) {
            n += generateIfNeeded(subscription, localDate) ? 1 : 0;
        }

        return n;
    }

    // ako je taj dan u mjesecu, onda generirati
    private boolean generateIfNeeded(Subscription subscription, LocalDate localDate) {
        Date date = new Date(subscription.getSubscriptionStart().getTime());
        LocalDate generationDate = getGenerationDate(date, localDate);

        if (generationDate.until(localDate, ChronoUnit.DAYS) == 0) {
            generateReceipt(subscription, convertDate(generationDate));
            return true;
        }
        return false;
    }

    private LocalDate getGenerationDate(Date subscDate, LocalDate checkDate) {
        LocalDate dateSS = convertDate(subscDate);
        long months = dateSS.until(checkDate, MONTHS);
        return dateSS.plusMonths(months);
    }

    @PostConstruct
    public void generateNonGeneratedReceipts() {
        Event event = eventRepository.findByName(LAST_GENERATED);

        if (event == null) event = generateNullEvent();
        else generate(event);

        event.setDate(convertDate(getYesterday()));
        eventRepository.save(event);
    }

    private Event generateNullEvent() {
        LocalDate yesterday = getYesterday();

        Event event = new Event();
        event.setName(LAST_GENERATED);

        int n = 0;
        for (Subscription subscription : subscriptionRepository.findAll()) {
            Date subStart = new Date(subscription.getSubscriptionStart().getTime());

            if (DAYS.between(yesterday, convertDate(subStart)) == 1) continue;
            generateReceipt(subscription, convertDate(getGenerationDate(subStart, yesterday)));
            n++;
        }

        log.info("generated " + n + " receipts");
        return event;
    }

    private void generate(Event event) {
        LocalDate yesterday = getYesterday();

        int n = 0;
        Date date = new Date(event.getDate().getTime());
        date = getNextDate(date);
        while (DAYS.between(convertDate(date), yesterday) >= 0) {
            List<Subscription> subscriptions =
                    subscriptionRepository.findAllBySubscriptionStartAndSubscriptionEndNull(date);

            for (Subscription subscription : subscriptions) {
                n++;
                Date subscDate = new Date(subscription.getSubscriptionStart().getTime());
                generateReceipt(subscription, convertDate(getGenerationDate(subscDate, convertDate(date))));
            }
            date = getNextDate(date);
        }
        log.info("generated " + n + " receipts");
    }

    private LocalDate getYesterday() {
        return convertDate(new Date()).minusDays(1);
    }

    private LocalDate convertDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date getNextDate(Date date) {
        LocalDate local = convertDate(date);
        local = local.plusDays(1);
        return convertDate(local);
    }

    private Date convertDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private void generateReceipt(Subscription subscription, Date date) {
        paymentService.generateReceipt(subscription, date);
    }

}
