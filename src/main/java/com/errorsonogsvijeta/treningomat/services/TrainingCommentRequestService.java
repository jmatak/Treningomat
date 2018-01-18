package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.TrainingCommentRequest;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.repository.TrainingCommentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingCommentRequestService {
    @Autowired
    private TrainingCommentRequestRepository trainingCommentRequestRepository;

    public List<TrainingCommentRequest> findTrainingCommentRequestsByAttendant(Attendant attendant) {
        return trainingCommentRequestRepository.findTrainingCommentRequestsByAttendant(attendant);
    }

    public List<TrainingCommentRequest> findTrainingCommentRequestsByTraining(Training training) {
        return trainingCommentRequestRepository.findTrainingCommentRequestsByTraining(training);
    }

    public void save(TrainingCommentRequest trainingCommentRequest) {
        trainingCommentRequestRepository.save(trainingCommentRequest);
    }

    public void delete(Integer id) {
        trainingCommentRequestRepository.delete(id);
    }

    public TrainingCommentRequest findTrainingCommentRequestById(Integer id) {
        return trainingCommentRequestRepository.findTrainingCommentRequestById(id);
    }

}
