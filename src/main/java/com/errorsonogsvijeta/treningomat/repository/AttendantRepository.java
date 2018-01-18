package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendantRepository extends JpaRepository<Attendant, Integer> {
    Attendant findAttendantByUsername(String username);
    List<Attendant> findAllByTrainingGroups(TrainingGroup trainingGroup);
    Attendant findAttendantById(Integer id);
}

