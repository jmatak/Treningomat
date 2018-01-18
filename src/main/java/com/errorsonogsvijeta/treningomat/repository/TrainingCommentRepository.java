package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.TrainingComment;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingCommentRepository extends JpaRepository<TrainingComment, Integer> {
    List<TrainingComment> findTrainingCommentsByTraining(Training training);
}
