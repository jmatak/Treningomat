package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.TrainingComment;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingCommentRepository extends JpaRepository<TrainingComment, Integer> {
    List<TrainingComment> findTrainingCommentsByTraining(Training training);
}
