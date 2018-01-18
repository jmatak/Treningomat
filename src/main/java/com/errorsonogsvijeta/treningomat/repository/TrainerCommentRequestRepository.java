package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.TrainerCommentRequest;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerCommentRequestRepository extends JpaRepository<TrainerCommentRequest, Integer> {
    List<TrainerCommentRequest> findTrainerCommentRequestsByAttendantAndTrainer(Attendant attendant, Trainer trainer);
    List<TrainerCommentRequest> findTrainerCommentRequestsByAttendant(Attendant attendant);
    TrainerCommentRequest findTrainerCommentRequestById(Integer id);
}
