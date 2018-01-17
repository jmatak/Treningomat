package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
import com.errorsonogsvijeta.treningomat.model.administration.SubscriptionWarning;
import com.errorsonogsvijeta.treningomat.model.administration.TrainerComment;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TrainerCommentService trainerCommentService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private AttendantService attendantService;
    @Autowired
    private SubscriptionWarningService subscriptionWarningService;

    @RequestMapping(value = "/trainers", method = RequestMethod.GET)
    public ModelAndView showAllTrainers() {
        ModelAndView modelAndView = new ModelAndView("trainers");

        modelAndView.addObject("allCities", cityService.findAll());
        modelAndView.addObject("allSports", sportService.findAll());
        modelAndView.addObject("trainer", new Trainer());
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
        subscriptionService.subscribe(groupRequest);
        groupRequestService.deleteGroupRequest(id);

        return "redirect:/trainer/groupRequests";
    }


    @RequestMapping(value = "/trainer/groupRequest/decline/{id}", method = RequestMethod.POST)
    public String declineUserRequest(@PathVariable Integer id) {
        groupRequestService.deleteGroupRequest(id);

        return "redirect:/trainer/groupRequests";
    }

    @RequestMapping(value = "/trainer/info", method = RequestMethod.GET)
    public ModelAndView getTrainerInfo() {
        return getProfileInfoMAV();
    }

    @RequestMapping(value = "/trainer/edit", method = RequestMethod.POST)
    public ModelAndView editAttendantInfo(@Valid Trainer trainer, HttpServletRequest request) {
        Trainer thisTrainer = getLoggedTrainer();

        if (trainer.getPassword() != null &&
                !trainer.getPassword().isEmpty() &&
                !thisTrainer.getPassword().equals(trainer.getPassword())) {
            thisTrainer.setPassword(trainer.getPassword());
            trainerService.editPassword(thisTrainer);
        }

        thisTrainer.setCity(trainer.getCity());
        thisTrainer.setAddress(trainer.getAddress());
        trainerService.save(thisTrainer);

        return new ModelAndView("redirect:/trainer/info");
    }

    private ModelAndView getProfileInfoMAV() {
        ModelAndView modelAndView = new ModelAndView("trainer/personal-information");
        modelAndView.addObject("allCities", cityService.findAll());
        modelAndView.addObject("trainer", getLoggedTrainer());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addTrainer", method = RequestMethod.POST)
    public String createNewUser(@Valid Trainer trainer, BindingResult trainerResult, HttpServletRequest request, RedirectAttributes attrs) {
        MultipartFile file = trainer.getFile();
        String fileName = "trainer_" + trainer.getUsername() + Util.getExtension(file.getOriginalFilename());
        String subdir = "trainers";
        String msg = Util.writeToFile(file, subdir, fileName, request);

        if (msg == null) {
            trainerService.saveTrainer(trainer, fileName);
            attrs.addFlashAttribute("message", "Trener je uspješno dodan.");
        } else {
            attrs.addFlashAttribute("message", msg);
        }

        return "redirect:/trainers";
    }

    @RequestMapping(value = "/trainer/trainings", method = RequestMethod.GET)
    public ModelAndView getTrainings() {
        ModelAndView modelAndView = new ModelAndView("trainings");

        Trainer trainer = getLoggedTrainer();
        List<Training> trainings = trainingService.findTrainingsByTrainingGroupInAndStartsAtBefore(trainer.getTrainingGroups(), new Date());

        modelAndView.addObject("training", new Training());
        modelAndView.addObject("trainings", trainings);
        return modelAndView;
    }

    @RequestMapping(value = "/comments/trainer/{id}", method = RequestMethod.GET)
    public ModelAndView getTrainerComments(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("trainer/comments");

        Trainer trainer = trainerService.findTrainerById(id);
        if (trainer == null) {
            return new ModelAndView("redirect:/error");
        }
        List<TrainerComment> trainerComments = trainerCommentService.findTrainerCommentsByTrainer(trainer);

        modelAndView.addObject("trainer", trainer);
        modelAndView.addObject("trainerComments", trainerComments);
        return modelAndView;
    }

    @RequestMapping(value = "/trainer/warning/{aid}/{tid}/send", method = RequestMethod.POST)
    public String sendWarning(@PathVariable Integer aid,@PathVariable Integer tid) {
        ModelAndView modelAndView = new ModelAndView("trainer/group_attendants");

        Attendant attendant = attendantService.findAttendantById(aid);
        TrainingGroup group = trainingGroupService.findTrainingGroupById(tid);

        SubscriptionWarning warning = new SubscriptionWarning(attendant, group);
        subscriptionWarningService.save(warning);

        return "redirect:/trainer/group/" + tid + "/attendants";
    }

    private Trainer getLoggedTrainer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainerService.findTrainerByUsername(user.getUsername());
    }
}
