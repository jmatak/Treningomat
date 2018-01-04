package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.TrainerService;
import com.errorsonogsvijeta.treningomat.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/training")
public class TrainingController {
    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainingService trainingService;

    @RequestMapping(value = "/newTraining", method = RequestMethod.GET)
    public ModelAndView newTraining() {
        ModelAndView modelAndView = new ModelAndView("calendar/new_training");

        Trainer trainer = getLoggedTrainer();

        modelAndView.addObject("training", new Training());
        modelAndView.addObject("allGroups", trainer.getTrainingGroups());
        return modelAndView;
    }

    @RequestMapping(value = "/newTraining", method = RequestMethod.POST)
    public String newTraining(@Valid Training training, BindingResult result) {
        trainingService.save(training);

        return "redirect:/training/newTraining";
    }

    private Trainer getLoggedTrainer() {
        org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainerService.findTrainerByUsername(loggedUser.getUsername());
    }

    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH:mm");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }
}
