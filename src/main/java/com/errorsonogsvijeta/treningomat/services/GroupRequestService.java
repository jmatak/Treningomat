package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.repository.GroupRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Patrik
 */
@Service
public class GroupRequestService {

    @Autowired
    private GroupRequestRepository groupRequestRepository;


    public void save(GroupRequest groupRequest) {
        groupRequestRepository.save(groupRequest);
    }

    public List<GroupRequest> getTrainersGroupRequests(Trainer trainer) {
        List<TrainingGroup> trainingGroups = trainer.getTrainingGroups();
        return groupRequestRepository.findAllByToTrainingGroupIn(trainingGroups);
    }

    public GroupRequest getGroupRequestById(Integer id) {
        return groupRequestRepository.findOne(id);
    }

    public void deleteGroupRequest(Integer id) {
        groupRequestRepository.delete(id);
    }

    public List<GroupRequest> getAllByTrainingGroupIn(List<TrainingGroup> trainingGroups){
        return groupRequestRepository.findAllByToTrainingGroupIn(trainingGroups);
    }

    public List<GroupRequest> getAllByTrainingGroup(TrainingGroup trainingGroup){
        return groupRequestRepository.findAllByToTrainingGroup(trainingGroup);
    }


}

