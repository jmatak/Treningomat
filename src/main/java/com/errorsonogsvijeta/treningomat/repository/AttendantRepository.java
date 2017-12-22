package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO: opis
 *
 * @author Matej Pipalović
 * @version 1.0
 */
@Repository
public interface AttendantRepository extends JpaRepository<Attendant, Integer> {
    Attendant findAttendantByUsername(String username);

    List<Attendant> findAllByTrainingGroups(TrainingGroup trainingGroup);

    Attendant findAttendantsById(Integer id);


}

