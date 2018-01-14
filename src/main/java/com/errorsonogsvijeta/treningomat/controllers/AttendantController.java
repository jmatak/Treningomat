package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.*;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
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
    @Autowired
    private TrainingCommentRequestService trainingCommentRequestService;
    @Autowired
    private TrainingCommentService trainingCommentService;
    @Autowired
    private TrainerCommentRequestService trainerCommentRequestService;
    @Autowired
    private TrainerCommentService trainerCommentService;
    @Autowired
    private CityService cityService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TrainerService trainerService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView viewProfile() {
        ModelAndView modelAndView = new ModelAndView("attendant/profile");

        Attendant attendant = getLoggedAttendant();
        List<Receipt> receipts = paymentService.getAllNonPaidReceiptsOfAttendant(attendant);
        List<TrainingCommentRequest> trainingCommentRequests = trainingCommentRequestService.findTrainingCommentRequestsByAttendant(
                attendant);
        List<TrainerCommentRequest> trainerCommentRequests = trainerCommentRequestService.findTrainerCommentRequestsByAttendant(
                attendant);

        modelAndView.addObject("commentSubscription", attendant.getCommentSubscription());
        modelAndView.addObject("allTrainingCommentRequests", trainingCommentRequests);
        modelAndView.addObject("allTrainerCommentRequests", trainerCommentRequests);
        modelAndView.addObject("allNonPaidReceipts", receipts);
        modelAndView.addObject("comment", new TrainingComment());
        return modelAndView;
    }


    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ModelAndView getGroups() {
        ModelAndView modelAndView = new ModelAndView("my_groups");

        Attendant attendant = getLoggedAttendant();
        List<TrainingGroup> trainingGroups = trainingGroupService.getAllGroupsOfAttendant(attendant);
        modelAndView.addObject("allTrainingGroups", trainingGroups);
        modelAndView.addObject("group", new TrainingGroup());
        modelAndView.addObject("trainer", new Trainer());

        return modelAndView;
    }

    @RequestMapping(value = "/group/{id}/leave", method = RequestMethod.POST)
    public ModelAndView leaveGroup(@PathVariable Integer id) {
        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroup(id);
        Attendant attendant = getLoggedAttendant();

        trainingGroup.getAttendants().remove(attendant);
        trainingGroupService.saveTrainingGroup(trainingGroup);

        return new ModelAndView("redirect:/attendant/groups");
    }

    @Deprecated // komentari se pisu na /attendant/profile
    @RequestMapping(value = "/trainingComments", method = RequestMethod.GET)
    public ModelAndView getTrainingCommentRequests() {
        ModelAndView modelAndView = new ModelAndView("attendant/training_comment_requests");

        Attendant attendant = getLoggedAttendant();
        List<TrainingCommentRequest> requests = trainingCommentRequestService.findTrainingCommentRequestsByAttendant(
                attendant);

        modelAndView.addObject("allTrainingCommentRequests", requests);
        return modelAndView;
    }

    @Deprecated
    @RequestMapping(value = "/trainingComments/comment/{id}", method = RequestMethod.GET)
    public ModelAndView writeTrainingComment(@PathVariable Integer id) {
//        ModelAndView modelAndView = new ModelAndView("attendant/training_comment");
//
//        TrainingCommentRequest request = trainingCommentRequestService.findTrainingCommentRequestById(id);
//
//        modelAndView.addObject("request", request);
//        modelAndView.addObject("comment", new TrainingComment());

        return new ModelAndView("redirect:/attendant/profile");
    }

    @Deprecated
    @RequestMapping(value = "/trainingComments/comment/{id}", method = RequestMethod.POST)
    public String commentTraining(
            @PathVariable Integer id, @Valid TrainingComment comment, BindingResult trainerResult
    ) {
        TrainingCommentRequest request = trainingCommentRequestService.findTrainingCommentRequestById(id);

        comment.setAttendant(request.getAttendant());
        comment.setTraining(request.getTraining());
        trainingCommentService.save(comment);
        trainingCommentRequestService.delete(id);

        return  "redirect:/attendant/profile";
    }

    @RequestMapping(value = "/trainingComments/delete/{id}", method = RequestMethod.POST)
    public String deleteTrainingCommentRequest(@PathVariable Integer id) {
        trainingCommentRequestService.delete(id);
        return  "redirect:/attendant/profile";
    }

    @Deprecated
    @RequestMapping(value = "/trainerComments", method = RequestMethod.GET)
    public ModelAndView getTrainerCommentRequests() {
        ModelAndView modelAndView = new ModelAndView("attendant/trainer_comment_requests");

        Attendant attendant = getLoggedAttendant();
        List<TrainerCommentRequest> requests = trainerCommentRequestService.findTrainerCommentRequestsByAttendant(
                attendant);

        modelAndView.addObject("allTrainerCommentRequests", requests);
        return modelAndView;
    }

    @Deprecated
    @RequestMapping(value = "/trainerComments/comment/{id}", method = RequestMethod.GET)
    public ModelAndView writeTrainerComment(@PathVariable Integer id) {
//        ModelAndView modelAndView = new ModelAndView("attendant/trainer_comment");
//
//        TrainerCommentRequest request = trainerCommentRequestService.findTrainerCommentRequestById(id);
//
//        modelAndView.addObject("request", request);
//        modelAndView.addObject("comment", new TrainingComment());

        return  new ModelAndView("redirect:/attendant/profile");
    }

    @RequestMapping(value = "/trainerComments/comment/{id}", method = RequestMethod.POST)
    public String commentTraining(
            @PathVariable Integer id, @Valid TrainerComment comment, BindingResult trainerResult
    ) {
        TrainerCommentRequest request = trainerCommentRequestService.findTrainerCommentRequestById(id);

        comment.setAttendant(request.getAttendant());
        comment.setTrainer(request.getTrainer());
        trainerCommentService.save(comment);
        trainerCommentRequestService.delete(id);

        return  "redirect:/attendant/profile";
    }

    @RequestMapping(value = "/trainerComments/delete/{id}", method = RequestMethod.POST)
    public String deleteTrainerCommentRequest(@PathVariable Integer id) {
        trainerCommentRequestService.delete(id);

        return  "redirect:/attendant/profile";
    }


    @Deprecated // navodno se ne koristi
    @RequestMapping(value = "/commentSubscription", method = RequestMethod.POST)
    public String changeCommentSubscription() {
        Attendant attendant = getLoggedAttendant();
        attendant.setCommentSubscription(!attendant.getCommentSubscription());
        attendantService.save(attendant);

        return "redirect:/attendant/profile";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView getAttendantInfo() {
        return getProfileInfoMAV();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editAttendantInfo(@Valid Attendant attendant, HttpServletRequest request) {
        Attendant thisAttendant = getLoggedAttendant();

        if (attendant.getPassword() != null &&
                !attendant.getPassword().isEmpty() &&
                !thisAttendant.getPassword().equals(attendant.getPassword())) {
            thisAttendant.setPassword(attendant.getPassword());
            attendantService.editPassword(thisAttendant);
        }

        MultipartFile file = attendant.getFile();
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            thisAttendant.setFile(file);
            String fileName = "attendant_" + thisAttendant.getUsername() + Util.getExtension(
                    file.getOriginalFilename());
            String subdir = "attendants";
            String msg = Util.writeToFile(file, subdir, fileName, request);

            if (msg != null) return error(msg);

            thisAttendant.setFile(attendant.getFile());
            thisAttendant.setIdPhoto(fileName);
        }

        thisAttendant.setCity(attendant.getCity());
        thisAttendant.setCommentSubscription(attendant.getCommentSubscription());
        thisAttendant.setStreetAndNumber(attendant.getStreetAndNumber());
        attendantService.save(thisAttendant);

        return new ModelAndView("redirect:/attendant/info");
    }

    private ModelAndView error(String message) {
        ModelAndView modelAndView = getProfileInfoMAV();
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    private ModelAndView getProfileInfoMAV() {
        ModelAndView modelAndView = new ModelAndView("attendant/personal-information");
        modelAndView.addObject("allCities", cityService.findAll());
        modelAndView.addObject("attendant", getLoggedAttendant());
        return modelAndView;
    }

    @RequestMapping(value = "/trainings", method = RequestMethod.GET)
    public ModelAndView getTrainings() {
        ModelAndView modelAndView = new ModelAndView("trainings");

        Attendant attendant = getLoggedAttendant();
        List<Training> trainings = trainingService.findTrainingsByAttendantsContainsAndStartAtBefore(attendant, new Date());


        modelAndView.addObject("training", new Training());
        modelAndView.addObject("trainings", trainings);
        return modelAndView;
    }

    @RequestMapping(value = "/trainers", method = RequestMethod.GET)
    public ModelAndView getTrainers() {
        ModelAndView modelAndView = new ModelAndView("trainers");

        Attendant attendant = getLoggedAttendant();
        List<Trainer> trainers = trainerService.findTrainersByTrainingGroupsIn(attendant.getTrainingGroups());


        modelAndView.addObject("trainer", new Trainer());
        modelAndView.addObject("allTrainers", trainers);
        return modelAndView;
    }

    private Attendant getLoggedAttendant() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendantService.findAttendantByUsername(user.getUsername());
    }
}
