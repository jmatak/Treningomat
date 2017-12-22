package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.repository.AttendantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Patrik
 */
@Service
public class AttendantService {

    @Autowired
    AttendantRepository attendantRepository;

    public List<Attendant> getAllAttendantsOfAGroup(TrainingGroup trainingGroup) {
        return attendantRepository.findAllByTrainingGroups(trainingGroup);
    }

}
