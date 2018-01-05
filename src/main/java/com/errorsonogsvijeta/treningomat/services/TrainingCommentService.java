package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.TrainingComment;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.repository.TrainingCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingCommentService {
    @Autowired
    private TrainingCommentRepository trainingCommentRepository;

    public List<TrainingComment> findTrainingCommentsByTraining(Training training) {
        return trainingCommentRepository.findTrainingCommentsByTraining(training);
    }

    public void save(TrainingComment comment) {
        trainingCommentRepository.save(comment);
    }
}
