package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.Receipt;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.services.AttendantService;
import com.errorsonogsvijeta.treningomat.services.PaymentService;
import com.errorsonogsvijeta.treningomat.services.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/attendant")
public class AttendantController {
    @Autowired
    private AttendantService attendantService;
    @Autowired
    private TrainingGroupService trainingGroupService;
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView viewProfile() {
        ModelAndView modelAndView = new ModelAndView("/attendant/profile");

        Attendant attendant = getLoggedAttendant();
        List<Receipt> receipts = paymentService.getAllNonPaidReceiptsOfAttendant(attendant);
        if (receipts == null) {
            receipts = Collections.emptyList();
        }

        modelAndView.addObject("allNonPaidReceipts", receipts);
        return modelAndView;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ModelAndView getGroups() {
        ModelAndView modelAndView = new ModelAndView("/attendant/groups");

        Attendant attendant = getLoggedAttendant();
        List<TrainingGroup> trainingGroups = trainingGroupService.getAllGroupsOfAttendant(attendant);
        modelAndView.addObject("allTrainingGroups", trainingGroups);

        return modelAndView;
    }

    private Attendant getLoggedAttendant() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendantService.findAttendantByUsername(user.getUsername());
    }
}
