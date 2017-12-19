package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Patrik
 */
@Repository
public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, Integer> {

    List<TrainingGroup> findAllByTrainer(Trainer trainer);
}