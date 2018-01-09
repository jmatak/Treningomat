package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Patrik
 */
@Repository
public interface GroupRequestRepository extends JpaRepository<GroupRequest, Integer> {

    List<GroupRequest> findAllByToTrainingGroupIn(List<TrainingGroup> trainingGroups);

    List<GroupRequest> findAllByToTrainingGroup(TrainingGroup trainingGroup);

    @Transactional
    void deleteAllByToTrainingGroup(TrainingGroup group);

}
