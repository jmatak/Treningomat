package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
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
import java.util.List;

@Controller
public class TrainerController {
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private SportService sportService;
    @Autowired
    private CityService cityService;
    @Autowired
    private GroupRequestService groupRequestService;
    @Autowired
    private TrainingGroupService trainingGroupService;

    @RequestMapping(value = "/trainers", method = RequestMethod.GET)
    public ModelAndView showAllTrainers() {
        ModelAndView modelAndView = new ModelAndView("trainers");

        modelAndView.addObject("allTrainers", trainerService.findAll());

        return modelAndView;
    }

    @RequestMapping(value = "/trainer/groupRequests", method = RequestMethod.GET)
    public ModelAndView showGroupRequests() {
        ModelAndView modelAndView = new ModelAndView("trainer/trainer_group_requests");

        Trainer trainer = getLoggedTrainer();
        List<GroupRequest> groupRequests = groupRequestService.getTrainersGroupRequests(trainer);

        modelAndView.addObject("allGroupRequests", groupRequests);
        return modelAndView;
    }

    @RequestMapping(value = "/trainer/groupRequest/accept/{id}", method = RequestMethod.POST)
    public String acceptUserRequest(@PathVariable Integer id) {
        GroupRequest groupRequest = groupRequestService.getGroupRequestById(id);

        Attendant attendant = groupRequest.getAttendant();
        TrainingGroup toTrainingGroup = groupRequest.getToTrainingGroup();
        List<Attendant> attendants = toTrainingGroup.getAttendants();
        attendants.add(attendant);

        trainingGroupService.saveTrainingGroup(toTrainingGroup);
        groupRequestService.deleteGroupRequest(id);

        return "redirect:/trainer/groupRequests";
    }


    @RequestMapping(value = "/trainer/groupRequest/decline/{id}", method = RequestMethod.POST)
    public String declineUserRequest(@PathVariable Integer id) {
        groupRequestService.deleteGroupRequest(id);

        return "redirect:/trainer/groupRequests";
    }

    @RequestMapping(value = "/admin/addTrainer", method = RequestMethod.GET)
    public ModelAndView addTrainer() {
        return createTrainerModelAndView();
    }

    @RequestMapping(value = "/admin/addTrainer", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Trainer trainer, BindingResult trainerResult, HttpServletRequest request) {
        ModelAndView modelAndView = createTrainerModelAndView();

        MultipartFile file = trainer.getFile();
        String fileName = "trainer_" + trainer.getUsername() + Util.getExtension(file.getOriginalFilename());
        String subdir = "trainers";
        String msg = Util.writeToFile(file, subdir, fileName, request);

        if (msg == null) {
            trainerService.saveTrainer(trainer, fileName);
            modelAndView.addObject("message", "Trener je uspješno dodan.");
        } else {
            modelAndView.addObject("message", msg);
        }

        return modelAndView;
    }

    private ModelAndView createTrainerModelAndView() {
        ModelAndView modelAndView = new ModelAndView("admin/add_trainer");

        modelAndView.addObject("allCities", cityService.findAll());
        modelAndView.addObject("allSports", sportService.findAll());
        modelAndView.addObject("trainer", new Trainer());

        return modelAndView;
    }

    private Trainer getLoggedTrainer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainerService.findTrainerByUsername(user.getUsername());
    }
}
