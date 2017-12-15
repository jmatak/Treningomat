package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.TrainerService;
import com.errorsonogsvijeta.treningomat.services.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Patrik
 */

@Controller
public class TrainingGroupController {

    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainingGroupService trainingGroupService;

    @RequestMapping(value = "/trainer/addTrainingGroup", method = RequestMethod.GET)
    public ModelAndView addGroup() {
        return getAddGroupModelAndView("");
    }


    //TODO: dodaj u JavaScriptu provjeru jeli korisnik zapravo popunio sva polja
    @RequestMapping(value = "/trainer/addTrainingGroup", method = RequestMethod.POST)
    public ModelAndView createNewGroup(@Valid TrainingGroup trainingGroup, BindingResult groupResult, HttpServletRequest request) {
        //TODO: dodaj obradu iznimki
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Trainer trainer = trainerService.findTrainerByUsername(user.getUsername());

        trainingGroup.setSport(trainer.getSport());
        trainingGroup.setTrainer(trainer);

        String message;
        try {
            trainingGroupService.saveTrainingGroup(trainingGroup);
            message = "Training group successfully added.";
        } catch (Exception e) {
            message = "Failed to add training group!";
        }

        return getAddGroupModelAndView(message);
    }

    private ModelAndView getAddGroupModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("trainingGroup", new TrainingGroup());
        modelAndView.setViewName("trainer/add_training_group");
        if (!message.equals("")) {
            modelAndView.addObject("message", message);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/trainer/groups", method = RequestMethod.GET)
    public ModelAndView viewTrainersGroups() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Trainer trainer = trainerService.findTrainerByUsername(user.getUsername());

        List<TrainingGroup> trainingGroups = trainingGroupService.getTrainersTrainingGroups(trainer);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allTrainingGroups", trainingGroups);
        modelAndView.setViewName("/trainer/trainer_groups");

        return modelAndView;
    }

    @RequestMapping(value = "/trainer/groups/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteGroup(@PathVariable("id") String id) {
        if (id != null) {
            trainingGroupService.deleteTrainingGroup(Integer.parseInt(id));
        }

        return new ModelAndView("redirect:" + "/trainer/groups");
    }

}
