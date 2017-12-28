package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.services.AttendantService;
import com.errorsonogsvijeta.treningomat.services.GroupRequestService;
import com.errorsonogsvijeta.treningomat.services.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Patrik
 */
@Controller
public class GroupRequestController {

    @Autowired
    private GroupRequestService groupRequestService;

    @Autowired
    private TrainingGroupService trainingGroupService;

    @Autowired
    private AttendantService attendantService;

    //TODO: dodaj da se ne moze slati vise istih zahtjeva!
    @RequestMapping(value = "/group/{id}/request", method = RequestMethod.POST)
    public ModelAndView saveGroupRequest(@PathVariable("id") Integer groupId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Attendant attendant = attendantService.findAttendantByUsername(user.getUsername());

        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroup(groupId);

        //TODO: dali da redirecta opet na listu grupa ili negdje drugdje
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setAttendant(attendant);
        groupRequest.setToTrainingGroup(trainingGroup);

        List<GroupRequest> groupRequests = groupRequestService.getAllByTrainingGroup(trainingGroup);
        if(!groupRequests.contains(groupRequest) && !trainingGroup.getAttendants().contains(attendant)) {
            groupRequestService.save(groupRequest);
        }

        return new ModelAndView("redirect:" + "/sport/" + trainingGroup.getSport().getId()   +"/groups");
    }

}
