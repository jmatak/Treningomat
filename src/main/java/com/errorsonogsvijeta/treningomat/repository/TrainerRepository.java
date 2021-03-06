package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Trainer findTrainerById(Integer id);
    Trainer findTrainerByUsername(String username);
    List<Trainer> findTrainersByTrainingGroupsIn(List<TrainingGroup> groups);
    @Override
    List<Trainer> findAll();
}
