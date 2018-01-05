package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.TrainingComment;
import com.errorsonogsvijeta.treningomat.model.administration.TrainingCommentRequest;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.TrainerService;
import com.errorsonogsvijeta.treningomat.services.TrainingCommentRequestService;
import com.errorsonogsvijeta.treningomat.services.TrainingCommentService;
import com.errorsonogsvijeta.treningomat.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/training")
public class TrainingController {
    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingCommentRequestService trainingCommentRequestService;

    @Autowired
    private TrainingCommentService trainingCommentService;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView showTraining(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("calendar/training");

        Training training = trainingService.findTrainingById(id);
        List<TrainingComment> comments = trainingCommentService.findTrainingCommentsByTraining(training);

        modelAndView.addObject("comments", comments);
        modelAndView.addObject("isEditable", training.getEndsAt().before(new Date()));
        modelAndView.addObject("training" , training);
        return modelAndView;
    }

    // https://stackoverflow.com/questions/17692941/values-for-thfield-attributes-in-checkbox
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String addAttendants(@PathVariable Integer id, @ModelAttribute(value="training") Training trainingAttendants) {

        Training training = trainingService.findTrainingById(id);
        training.setAttendants(trainingAttendants.getAttendants());

        addTrainingCommentRequests(training);

        trainingService.save(training);

        return "redirect:/calendar/";
    }

    private void addTrainingCommentRequests(Training training) {
        List<TrainingCommentRequest> requests = trainingCommentRequestService.findTrainingCommentRequestsByTraining(training);
        for (Attendant attendant : training.getAttendants()) {
            if (attendant.getCommentSubscription()) {
                TrainingCommentRequest request = new TrainingCommentRequest(attendant, training);
                if (! requests.contains(request)) {
                    trainingCommentRequestService.save(request);
                }
            }
        }
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
