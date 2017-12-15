package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Patrik
 */
@Repository
public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, Integer> {

}
