package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.TrainerCommentRequest;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.repository.TrainerCommentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerCommentRequestService {
    @Autowired
    private TrainerCommentRequestRepository trainerCommentRequestRepository;

    public List<TrainerCommentRequest> findTrainerCommentRequestsByAttendantAndTrainer(Attendant attendant, Trainer trainer) {
        return trainerCommentRequestRepository.findTrainerCommentRequestsByAttendantAndTrainer(attendant,trainer);
    }

    public List<TrainerCommentRequest> findTrainerCommentRequestsByAttendant(Attendant attendant) {
        return trainerCommentRequestRepository.findTrainerCommentRequestsByAttendant(attendant);
    }

    public TrainerCommentRequest findTrainerCommentRequestById(Integer id) {
        return trainerCommentRequestRepository.findTrainerCommentRequestById(id);
    }

    public void save(TrainerCommentRequest trainerCommentRequest) {
        trainerCommentRequestRepository.save(trainerCommentRequest);
    }

    public void delete(Integer id) {
        trainerCommentRequestRepository.delete(id);
    }


}
