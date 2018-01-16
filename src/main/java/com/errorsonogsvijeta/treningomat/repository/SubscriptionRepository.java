package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.Subscription;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    List<Subscription> findAllBySubscriptionStartAndSubscriptionEndNull(Date date);
//    List<Subscription> findAllBySubscriptionEndNull();
    List<Subscription> findAllBySubscriptionEndNullOrSubscriptionEnd(Date date);
    List<Subscription> findAll();
    Subscription findAllByAttendantAndGroupAndSubscriptionEndNull(Attendant attendant, TrainingGroup group);
}
