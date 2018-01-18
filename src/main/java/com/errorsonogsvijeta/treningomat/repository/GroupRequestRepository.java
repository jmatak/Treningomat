package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupRequestRepository extends JpaRepository<GroupRequest, Integer> {
    List<GroupRequest> findAllByToTrainingGroupIn(List<TrainingGroup> trainingGroups);
    List<GroupRequest> findAllByToTrainingGroup(TrainingGroup trainingGroup);
    List<GroupRequest> findGroupRequestsByAttendantAndToTrainingGroupIn(
            Attendant attendant, List<TrainingGroup> trainingGroups
    );
    @Transactional
    void deleteAllByToTrainingGroup(TrainingGroup group);
}
