package com.errorsonogsvijeta.treningomat.repository;

<<<<<<< HEAD
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TODO: opis
 *
 * @author Matej Pipalović
 * @version 1.0
 */
public interface AttendantRepository extends JpaRepository<Attendant, Integer> {
    public Attendant findAttendantByUsername(String username);

    List<Attendant> findAllByTrainingGroups(TrainingGroup trainingGroup);

=======
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO: opis
 *
 * @author Matej Pipalović
 * @version 1.0
 */
public interface AttendantRepository extends JpaRepository<Attendant, Integer> {
    public Attendant findAttendantByUsername(String username);
>>>>>>> origin/develop
}
