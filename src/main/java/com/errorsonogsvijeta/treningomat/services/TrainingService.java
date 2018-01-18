package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;

    public Training findTrainingById(Integer id) {
        return trainingRepository.findTrainingById(id);
    }

    public Integer countDistinctByAttendantsIsInAndTrainingGroup_TrainerIs(Attendant attendant, Trainer trainer) {
        return trainingRepository.countTrainingsWhereAttendantAndTrainerAre(attendant.getId(), trainer.getId());
    }

    public List<Training> findTrainingsByTrainingGroupIn(List<TrainingGroup> groups) {
        return trainingRepository.findTrainingsByTrainingGroupIn(groups);
    }

    public List<Training> findTrainingsByTrainingGroupInAndStartsAtBefore(List<TrainingGroup> groups, Date date) {
        return trainingRepository.findTrainingsByTrainingGroupInAndStartAtBefore(groups, date);
    }

    public List<Training> findTrainingsByAttendantsContainsAndStartAtBefore(Attendant attendant, Date date) {
        return trainingRepository.findTrainingsByAttendantsContainsAndStartAtBefore(attendant, date);
    }

    public List<Training> findAll() {
        return trainingRepository.findAll();
    }

    public void save(Training training) {
        trainingRepository.save(training);
    }

    public void delete(Training training) {
        trainingRepository.delete(training);
    }
}
