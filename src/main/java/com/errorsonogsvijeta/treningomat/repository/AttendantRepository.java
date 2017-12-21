package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Patrik
 */
@Repository
public interface AttendantRepository extends JpaRepository<Attendant, Integer> {

    List<Attendant> findAllByTrainingGroups(TrainingGroup trainingGroup);

}
