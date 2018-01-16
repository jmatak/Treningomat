package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
import com.errorsonogsvijeta.treningomat.model.administration.Subscription;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // todo izvrsiti na odgovarajucim mjestima
    void unsuscribe(Attendant attendant, TrainingGroup group) {
        Subscription subscription =
        subscriptionRepository.findAllByAttendantAndGroupAndSubscriptionEndNull(attendant, group);

        // todo visekratnik datuma pocetka pretplate
        subscription.setSubscriptionEnd(new Date());
        subscriptionRepository.save(subscription);
    }
}
