package com.errorsonogsvijeta.treningomat.scheduler;

import com.errorsonogsvijeta.treningomat.model.administration.Subscription;
import com.errorsonogsvijeta.treningomat.repository.SubscriptionRepository;
import com.errorsonogsvijeta.treningomat.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class ReceiptScheduler {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private PaymentService paymentService;

    private static final Logger log = LoggerFactory.getLogger(ReceiptScheduler.class);

    // https://stackoverflow.com/questions/26147044/spring-cron-expression-for-every-day-101am
    @Scheduled(cron = "0 30 3 * * ?")
    private void updateReceipts() {
        long ms1 = new Date().getTime();
        log.info("receipt generation started");
        int count = generateReceipts();
        long ms2 = new Date().getTime();
        log.info(count + " receipts generated in " + (ms2 - ms1) + "ms");
    }

    private int generateReceipts() {
        Date input = new Date();
        LocalDate today = convertDate(input);
        LocalDate yesterday = today.minusDays(1);
        return generateReceiptsFor(yesterday);
    }

    // izdvojno u metodu za dan, tako da ako server nije bio pokrenut neki dan,
    // mozemo generirati racune jedostavno pokretanjem ove metode

    // nisam se zamarao sa time dali ce uzastopno pozivanje ove metode
    // generirati vise racuna za isti tr... (spoiler: hoce)
    public int generateReceiptsFor(LocalDate localDate) {
        int n = 0;

        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriptionEndNull();
        for (Subscription subscription : subscriptions) {
            Date date = new Date(subscription.getSubscriptionStart().getTime());
            LocalDate dateSS = convertDate(date);
            long months = dateSS.until(localDate, ChronoUnit.MONTHS);
            LocalDate receiptDateGenerationDate = dateSS.plusMonths(months);

            if (receiptDateGenerationDate.until(localDate, ChronoUnit.DAYS) == 0) {
                generateReceipt(subscription);
                ++n;
            }
        }

        return n;
    }

    private LocalDate convertDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void generateReceipt(Subscription subscription) {
        paymentService.generateReceipt(subscription);
    }

}
