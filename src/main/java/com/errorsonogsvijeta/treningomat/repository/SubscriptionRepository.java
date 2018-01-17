package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.Subscription;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findByAttendantAndGroupAndSubscriptionEndNull(Attendant attendant, TrainingGroup group);
    List<Subscription> findAllBySubscriptionEndNullOrSubscriptionEndAfter(Date date);
    List<Subscription> findAllByAttendantAndGroupOrderByIdDesc(Attendant attendant, TrainingGroup group);
    List<Subscription> findAllBySubscriptionEndNull();

}
