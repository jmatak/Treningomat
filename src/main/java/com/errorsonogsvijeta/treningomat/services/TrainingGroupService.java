package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.repository.TrainingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Patrik
 */

@Service
public class TrainingGroupService {

    @Autowired
    private TrainingGroupRepository trainingGroupRepository;

    public void saveTrainingGroup(TrainingGroup trainingGroup) {
        trainingGroupRepository.save(trainingGroup);
    }

}
