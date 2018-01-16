package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
import com.errorsonogsvijeta.treningomat.model.administration.Subscription;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public void subscribe(GroupRequest request) {
        Subscription subscription = new Subscription();
        subscription.setAttendant(request.getAttendant());
        subscription.setGroup(request.getToTrainingGroup());
        subscription.setSubscriptionStart(new Date());

        subscriptionRepository.save(subscription);
    }

    // todo postoji li jos neki nacin napustanja grupe?
    public void unsuscribe(Attendant attendant, TrainingGroup group) {
        Subscription subscription =
        subscriptionRepository.findAllByAttendantAndGroupAndSubscriptionEndNull(attendant, group);

        subscription.setSubscriptionEnd(plusMonths(subscription.getSubscriptionStart(), 1));
        subscriptionRepository.save(subscription);
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
