package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.training.Sport;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.repository.TrainingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public TrainingGroup getTrainingGroup(Integer id){
        return trainingGroupRepository.findOne(id);
    }

    public List<TrainingGroup> getAllGroupsOfAttendant(Attendant attendant) {
        List<TrainingGroup> trainingGroups = trainingGroupRepository.findByAttendantsContains(attendant);
        if (trainingGroups == null) {
            trainingGroups = Collections.emptyList();
        }

        return trainingGroups;
    }

    public List<TrainingGroup> getTrainersBySport(Sport sport) {
        return trainingGroupRepository.findAllBySport(sport);
    }
}
