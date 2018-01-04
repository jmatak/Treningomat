package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendantRepository extends JpaRepository<Attendant, Integer> {
    Attendant findAttendantByUsername(String username);
    List<Attendant> findAllByTrainingGroups(TrainingGroup trainingGroup);
    Attendant findAttendantsById(Integer id);
}

