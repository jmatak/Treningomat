package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.calendar.Term;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.AttendantService;
import com.errorsonogsvijeta.treningomat.services.TrainerService;
import com.errorsonogsvijeta.treningomat.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CalendarController {
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private AttendantService attendantService;
    @Autowired
    private TrainerService trainerService;

    @RequestMapping(value = "attendant/calendar", method = RequestMethod.GET)
    public ModelAndView getAttendantCalendar() {
        ModelAndView modelAndView = new ModelAndView("calendar");

        List<TrainingGroup> groups = getLoggedAttendant().getTrainingGroups();
        List<Term> terms = Term.toTerms(trainingService.findTrainingsByTrainingGroupIn(groups));

        modelAndView.addObject("events", terms);
        return modelAndView;
    }

    @RequestMapping(value = "trainer/calendar", method = RequestMethod.GET)
    public ModelAndView getTrainerCalendar() {
        ModelAndView modelAndView = new ModelAndView("calendar");

        List<TrainingGroup> groups = groups = getLoggedTrainer().getTrainingGroups();
        List<Term> terms = Term.toTerms(trainingService.findTrainingsByTrainingGroupIn(groups));

        modelAndView.addObject("allGroups", groups);
        modelAndView.addObject("training", new Training());
        modelAndView.addObject("events", terms);
        return modelAndView;
    }

    private Attendant getLoggedAttendant() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendantService.findAttendantByUsername(user.getUsername());
    }

    private Trainer getLoggedTrainer() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainerService.findTrainerByUsername(user.getUsername());
    }
}
