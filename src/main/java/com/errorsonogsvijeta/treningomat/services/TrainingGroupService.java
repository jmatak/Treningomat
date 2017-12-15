package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.repository.TrainingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TrainingGroup> getTrainersTrainingGroups(Trainer trainer) {
        return trainingGroupRepository.findAllByTrainer(trainer);
    }

    public void deleteTrainingGroup(Integer id) {
        trainingGroupRepository.delete(id);
    }

}
