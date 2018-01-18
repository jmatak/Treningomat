package com.errorsonogsvijeta.treningomat.scheduler;

import com.errorsonogsvijeta.treningomat.model.Event;
import com.errorsonogsvijeta.treningomat.model.administration.Subscription;
import com.errorsonogsvijeta.treningomat.repository.EventRepository;
import com.errorsonogsvijeta.treningomat.services.PaymentService;
import com.errorsonogsvijeta.treningomat.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

@Component
public class ReceiptScheduler {
    private static final String LAST_GENERATED = "last_generated";
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private EventRepository eventRepository;

    private static final Logger log = LoggerFactory.getLogger(ReceiptScheduler.class);

    @Scheduled(cron = "0 0 1 * * ?")
    private void updateReceipts() {
        Date lastRun = new Date(eventRepository.findByName(LAST_GENERATED).getDate().getTime());

        if (DAYS.between(convertDate(lastRun), LocalDate.now()) == 0) {
            log.info("receipts already generated for today");
            return;
        }

        int count = generateReceipts();
        log.info(count + " receipts generated");
    }

    // for today
    private int generateReceipts() {
        LocalDate today = LocalDate.now();
        Event event = eventRepository.findByName(LAST_GENERATED);
        event.setDate(convertDate(today));

        int generated = generateReceiptsFor(today);
        eventRepository.save(event);
        return generated;
    }

    public int generateReceiptsFor(LocalDate localDate) {
        int n = 0;

        List<Subscription> subscriptions = subscriptionService.getNotEndedSubscriptions();
        for (Subscription subscription : subscriptions) {
            n += generateIfNeeded(subscription, localDate) ? 1 : 0;
        }

        return n;
    }

    private boolean generateIfNeeded(Subscription subscription, LocalDate localDate) {
        Date date = new Date(subscription.getSubscriptionStart().getTime());
        LocalDate generationDate = getGenerationDate(date, localDate);

        if (DAYS.between(generationDate, localDate) == 0) {
            generateReceipt(subscription, convertDate(generationDate));
            return true;
        }
        return false;
    }

    private LocalDate getGenerationDate(Date subscDate, LocalDate checkDate) {
        LocalDate dateSS = convertDate(subscDate);
        long months = MONTHS.between(dateSS, checkDate);
        if (months == 0) months++; // ++ because first receipt is generated upon joining the group
        return dateSS.plusMonths(months);
    }

    @PostConstruct
    public void generateNonGeneratedReceipts() {
        Event event = eventRepository.findByName(LAST_GENERATED);

        if (event == null) event = generateNullEvent();
        else generate(event);

        event.setDate(convertDate(LocalDate.now()));
        eventRepository.save(event);
    }

    private Event generateNullEvent() {
        LocalDate today = LocalDate.now();

        Event event = new Event();
        event.setName(LAST_GENERATED);

        int n = 0;
        for (Subscription subscription : subscriptionService.getNotEndedSubscriptions()) {
            Date subStart = new Date(subscription.getSubscriptionStart().getTime());

            LocalDate generationDate = getGenerationDate(subStart, LocalDate.now());
            if (DAYS.between(generationDate, today) >= 0) {
                generateReceipt(subscription, convertDate(generationDate));
                n++;
            }
        }
        log.info(n + " receipts generated");
        return event;
    }

    private void generate(Event event) {
        LocalDate date = convertDate(new Date(event.getDate().getTime()));
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        if (!today.isAfter(date)) return; // already generated

        int n = 0;
        List<Subscription> subscriptions = subscriptionService.getNotEndedSubscriptions();
        for (Subscription subscription : subscriptions) {
            Date subscriptionDate = new Date(subscription.getSubscriptionStart().getTime());
            LocalDate generationDate = getGenerationDate(subscriptionDate, date);

            if (generationDate.isAfter(date) && generationDate.isBefore(tomorrow)) {
                generateReceipt(subscription, convertDate(generationDate));
                n++;
            }
        }

        log.info(n + " receipts generated");
    }

    private LocalDate convertDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date convertDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private void generateReceipt(Subscription subscription, Date date) {
        paymentService.generateReceipt(subscription, date);
    }

}
