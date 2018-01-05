package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.TrainingComment;
import com.errorsonogsvijeta.treningomat.model.administration.TrainingCommentRequest;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingCommentRequestRepository extends JpaRepository<TrainingCommentRequest, Integer> {
    List<TrainingCommentRequest> findTrainingCommentRequestsByAttendant(Attendant attendant);

    List<TrainingCommentRequest> findTrainingCommentRequestsByTraining(Training training);

    TrainingCommentRequest findTrainingCommentRequestById(Integer id);
}
