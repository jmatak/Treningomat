package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.TrainerComment;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerCommentRepository extends JpaRepository<TrainerComment, Integer> {
    List<TrainerComment> findTrainerCommentsByTrainer(Trainer trainer);
}
