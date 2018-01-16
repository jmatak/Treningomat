package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.TrainerComment;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerCommentRepository extends JpaRepository<TrainerComment, Integer> {
    List<TrainerComment> findTrainerCommentsByTrainer(Trainer trainer);
}
