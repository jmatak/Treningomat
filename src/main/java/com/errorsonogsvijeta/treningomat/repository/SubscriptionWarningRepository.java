package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.SubscriptionWarning;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionWarningRepository extends JpaRepository<SubscriptionWarning, Integer> {
    List<SubscriptionWarning> findSubscriptionWarningsByAttendant(Attendant attendant);
    SubscriptionWarning findSubscriptionWarningById(Integer id);
}
