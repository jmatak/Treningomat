package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.TrainerCommentRequest;
import com.errorsonogsvijeta.treningomat.model.administration.TrainingComment;
import com.errorsonogsvijeta.treningomat.model.administration.TrainingCommentRequest;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.*;
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
    private final static int TRAININGS_COUNT = 2;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingCommentRequestService trainingCommentRequestService;

    @Autowired
    private TrainingCommentService trainingCommentService;

    @Autowired
    private TrainerCommentRequestService trainerCommentRequestService;

    @Autowired
    private TrainerCommentService trainerCommentService;

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

        addCommentRequests(training);

        trainingService.save(training);

        return "redirect:/calendar/";
    }

    private void addCommentRequests(Training training) {
        List<TrainingCommentRequest> requests = trainingCommentRequestService.findTrainingCommentRequestsByTraining(training);

        for (Attendant attendant : training.getAttendants()) {
            if (attendant.getCommentSubscription()) {
                TrainingCommentRequest request = new TrainingCommentRequest(attendant, training);
                if (! requests.contains(request)) {
                    trainingCommentRequestService.save(request);
                }
            }

            Integer count = trainingService.countDistinctByAttendantsIsInAndTrainingGroup_TrainerIs(attendant, training.getTrainingGroup().getTrainer());
            if (count % TRAININGS_COUNT == 0) {
                List<TrainerCommentRequest> tcRequests = trainerCommentRequestService.findTrainerCommentRequestsByAttendantAndTrainer(attendant, training.getTrainingGroup().getTrainer());
                TrainerCommentRequest request = new TrainerCommentRequest(attendant, training.getTrainingGroup().getTrainer());

                if (! tcRequests.contains(request)) {
                    trainerCommentRequestService.save(request);
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