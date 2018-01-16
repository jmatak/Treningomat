package com.errorsonogsvijeta.treningomat.repository;


import com.errorsonogsvijeta.treningomat.model.administration.SubscriptionWarning;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionWarningRepository extends JpaRepository<SubscriptionWarning, Integer> {
    List<SubscriptionWarning> findSubscriptionWarningsByAttendant(Attendant attendant);

    SubscriptionWarning findSubscriptionWarningById(Integer id);
}
