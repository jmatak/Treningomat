package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.*;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.repository.ReceiptRepository;
import com.errorsonogsvijeta.treningomat.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.MONTHS;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private PaymentService paymentService;

    public void subscribe(GroupRequest request) {
        List<Subscription> subscriptions =
        subscriptionRepository.findAllByAttendantAndGroupOrderByIdDesc(request.getAttendant(), request.getToTrainingGroup());

        if (!subscriptions.isEmpty()) {
            // last subscription
            Subscription subscription = subscriptions.get(0);
            LocalDate start = convertDate(new Date(subscription.getSubscriptionStart().getTime()));
            LocalDate end = convertDate(new Date(subscription.getSubscriptionEnd().getTime()));

            if (LocalDate.now().isAfter(start.minusDays(1)) && end.isAfter(LocalDate.now())) {
                resubscribe(subscription, request, end);
                return;
            }
        }
        subscribeNew(request);
    }

    private void resubscribe(Subscription subscription, GroupRequest request, LocalDate end) {
        Receipt lastReceipt = paymentService.getLastReceipt(subscription.getAttendant(), subscription.getGroup());
        if (lastReceipt == null) {
            subscriptionRepository.delete(subscription);
            subscribeNew(request);
            return;
        }

        LocalDate createdDate = convertDate(new Date(lastReceipt.getCreatedDate().getTime()));

        if (MONTHS.between(createdDate.plusMonths(1), end) == 0) {
            // receipt for last month exists
            subscription.setSubscriptionEnd(null);
            subscriptionRepository.save(subscription);
        } else {
            // receipt for last month does not exist(trainer deleted it -- only way)
            subscriptionRepository.delete(subscription);
            subscribeNew(request);
        }
    }

    private void subscribeNew(GroupRequest request) {
        Subscription subscription = new Subscription();
        subscription.setAttendant(request.getAttendant());
        subscription.setGroup(request.getToTrainingGroup());
        subscription.setSubscriptionStart(new Date());
        subscriptionRepository.save(subscription);
        paymentService.generateReceipt(subscription, convertDate(LocalDate.now()));
    }

    public void unsuscribe(Attendant attendant, TrainingGroup group) {
        Subscription subscription =
        subscriptionRepository.findByAttendantAndGroupAndSubscriptionEndNull(attendant, group);

        subscription.setSubscriptionEnd(plusMonths(subscription.getSubscriptionStart(), 1));
        subscriptionRepository.save(subscription);
    }

    public List<Subscription> getCurrentSubscriptions() {
        return subscriptionRepository.findAllBySubscriptionEndNullOrSubscriptionEndAfter(convertDate(LocalDate.now()));
    }

    public List<Subscription> getNotEndedSubscriptions() {
        return subscriptionRepository.findAllBySubscriptionEndNull();
    }

    public Subscription getLastSubscription(Attendant attendant, TrainingGroup trainingGroup) {
        List<Subscription> subs = subscriptionRepository.findAllByAttendantAndGroupOrderByIdDesc(attendant, trainingGroup);
        if (subs.isEmpty()) return null;
        return subs.get(0);
    }

    private Date plusMonths(Date date, int months) {
        return convertDate(convertDate(date).plusMonths(months));
    }

    // todo kopirano iz ReceiptSchedulera, izdvojiti negdje
    private LocalDate convertDate(Date date) {
        return new Date(date.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date convertDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
