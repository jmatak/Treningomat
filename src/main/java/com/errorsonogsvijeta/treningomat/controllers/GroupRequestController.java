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

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupRequestController {
    @Autowired
    private GroupRequestService groupRequestService;

    @Autowired
    private TrainingGroupService trainingGroupService;

    @Autowired
    private AttendantService attendantService;

    @RequestMapping(value = "/{id}/request", method = RequestMethod.POST)
    public String saveGroupRequest(@PathVariable("id") Integer groupId) {
        Attendant attendant = getLoggedAttendant();

        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroup(groupId);

        GroupRequest groupRequest = new GroupRequest(attendant, trainingGroup);

        List<GroupRequest> groupRequests = groupRequestService.getAllByTrainingGroup(trainingGroup);
        if (!groupRequests.contains(groupRequest) && !trainingGroup.getAttendants().contains(attendant)) {
            groupRequestService.save(groupRequest);
        }

        return "redirect:" + "/sport/" + trainingGroup.getSport().getId() + "/groups";
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
    public String cancelRequest(@PathVariable("id") Integer groupId) {
        Attendant attendant = getLoggedAttendant();

        TrainingGroup group = trainingGroupService.getTrainingGroup(groupId);
        GroupRequest request = groupRequestService.getGroupRequestsByAttendantAndToTrainingGroupIn(attendant, Collections.singletonList(group)).get(0);
        groupRequestService.deleteGroupRequest(request.getId());

        return "redirect:/sport/" + group.getSport().getId() + "/groups";
    }


    private Attendant getLoggedAttendant() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendantService.findAttendantByUsername(user.getUsername());
    }
}
