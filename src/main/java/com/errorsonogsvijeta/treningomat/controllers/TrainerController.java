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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class TrainerController {
    private static final String UPLOADED_FOLDER = "images";
    private static final String FILE_SEPARATOR = "/";

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

    @RequestMapping(value = "/admin/addTrainer", method = RequestMethod.GET)
    public ModelAndView addTrainer() {
        return createTrainerModelAndView();
    }

    @RequestMapping(value = "/admin/addTrainer", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Trainer trainer, BindingResult trainerResult, HttpServletRequest request) {
        ModelAndView modelAndView = createTrainerModelAndView();

        try {
            MultipartFile file = trainer.getFile();
            byte[] bytes = file.getBytes();
            Path dir = Paths.get(request.getServletContext().getRealPath("") + FILE_SEPARATOR + UPLOADED_FOLDER);

            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }

            String extension = getExtension(file.getOriginalFilename());

            if (extension.equals(".jpg") || extension.equals(".png") || extension.equals(".gif")) {
                String fileName = "trainer" + String.valueOf(trainer.hashCode()) + extension;
                Path path = Paths.get(dir + FILE_SEPARATOR + fileName);
                Files.write(path, bytes);
                trainerService.saveTrainer(trainer, fileName);

                modelAndView.addObject("message", "Trener je uspješno dodan.");
            } else {
                modelAndView.addObject("message", "Nevaljana ekstenzija slike!");
            }
        } catch (Exception e) {
            modelAndView.addObject("message", "Neuspješno dodavanje trenera.");
        }

        return modelAndView;
    }

    private ModelAndView createTrainerModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allCities", cityService.findAll());
        modelAndView.addObject("allSports", sportService.findAll());
        modelAndView.addObject("trainer", new Trainer());
        modelAndView.setViewName("admin/add_trainer");
        return modelAndView;
    }

    @RequestMapping(value = "/trainers", method = RequestMethod.GET)
    public ModelAndView showAllTrainers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allTrainers", trainerService.findAll());
        modelAndView.setViewName("trainers");
        return modelAndView;
    }

    private String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i);
        }
        return extension;
    }

    @RequestMapping(value = "/trainer/groupRequests", method = RequestMethod.GET)
    public ModelAndView showGroupRequests() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Trainer trainer = trainerService.findTrainerByUsername(user.getUsername());

        List<GroupRequest> groupRequests = groupRequestService.getTrainersGroupRequests(trainer);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allGroupRequests", groupRequests);
        modelAndView.setViewName("trainer/trainer_group_requests");

        return modelAndView;
    }


    @RequestMapping(value = "/trainer/groupRequest/accept/{id}", method = RequestMethod.POST)
    public ModelAndView acceptUserRequest(@PathVariable Integer id) {
        GroupRequest groupRequest = groupRequestService.getGroupRequestById(id);

        Attendant attendant = groupRequest.getAttendant();
        TrainingGroup toTrainingGroup = groupRequest.getToTrainingGroup();
        List<Attendant> attendants = toTrainingGroup.getAttendants();
        attendants.add(attendant);

        trainingGroupService.saveTrainingGroup(toTrainingGroup);
        groupRequestService.deleteGroupRequest(id);

        return new ModelAndView("redirect:" + "/trainer/groupRequests");
    }


    @RequestMapping(value = "/trainer/groupRequest/decline/{id}", method = RequestMethod.POST)
    public ModelAndView declineUserRequest(@PathVariable Integer id) {
        groupRequestService.deleteGroupRequest(id);

        return new ModelAndView("redirect:" + "/trainer/groupRequests");
    }

}
