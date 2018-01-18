package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.SubscriptionWarning;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.repository.SubscriptionWarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionWarningService {
    @Autowired
    SubscriptionWarningRepository subscriptionWarningRepository;

    public List<SubscriptionWarning> findSubscriptionWarningsByAttendant(Attendant attendant) {
        return subscriptionWarningRepository.findSubscriptionWarningsByAttendant(attendant);
    }

    public SubscriptionWarning findSubscriptionWarningById(Integer id) {
        return subscriptionWarningRepository.findSubscriptionWarningById(id);
    }

    public void save(SubscriptionWarning warning) {
        subscriptionWarningRepository.save(warning);
    }

    public void delete(SubscriptionWarning warning) {
        subscriptionWarningRepository.delete(warning);
    }
}
