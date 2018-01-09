package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.services.AttendantService;
import com.errorsonogsvijeta.treningomat.services.TrainerService;
import com.errorsonogsvijeta.treningomat.services.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TrainingGroupController {
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainingGroupService trainingGroupService;
    @Autowired
    private AttendantService attendantService;

    @RequestMapping(value = "/trainer/addTrainingGroup", method = RequestMethod.GET)
    public ModelAndView addGroup() {
        return getAddGroupModelAndView(null);
    }

    //TODO: JavaScriptu provjera je li korisnik zapravo popunio sva polja
    @RequestMapping(value = "/trainer/addTrainingGroup", method = RequestMethod.POST)
    public ModelAndView createNewGroup(@Valid TrainingGroup trainingGroup, BindingResult groupResult, HttpServletRequest request) {
        Trainer trainer = getLoggedTrainer();

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

    @RequestMapping(value = "/trainer/groups", method = RequestMethod.GET)
    public ModelAndView viewTrainersGroups() {
        ModelAndView modelAndView = new ModelAndView("my_groups");

        Trainer trainer = getLoggedTrainer();
        List<TrainingGroup> trainingGroups = trainingGroupService.getTrainersTrainingGroups(trainer);

        modelAndView.addObject("group", new TrainingGroup());
        modelAndView.addObject("trainer", trainer);
        modelAndView.addObject("allTrainingGroups", trainingGroups);

        return modelAndView;
    }

    @RequestMapping(value = "/trainer/groups/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editGroup(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("trainer/edit_group");

        Trainer trainer = getLoggedTrainer();
        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroup(id);

        modelAndView.addObject("trainingGroup", trainingGroup);
        modelAndView.addObject("trainersSports", trainer.getSports());
        return modelAndView;
    }

    //tu bi mozda trebao dodati i izbacivanje korisnika iz grupe, da u prikazu
    @RequestMapping(value = "/trainer/groups/edit/{id}", method = RequestMethod.POST)
    public String updateGroup(@PathVariable("id") Integer id, HttpServletRequest request, TrainingGroup trainingGroup, RedirectAttributes redirectAttributes) {
        TrainingGroup oldTrainingGroup = trainingGroupService.getTrainingGroup(id);

        boolean changed = false;
        if (!oldTrainingGroup.getName().equals(trainingGroup.getName())) {
            oldTrainingGroup.setName(trainingGroup.getName());
            changed = true;
        }
        if (!oldTrainingGroup.getPlace().equals(trainingGroup.getPlace())) {
            oldTrainingGroup.setPlace(trainingGroup.getPlace());
            changed = true;
        }
        if (!oldTrainingGroup.getAmount().equals(trainingGroup.getAmount())) {
            oldTrainingGroup.setAmount(trainingGroup.getAmount());
            changed = true;
        }

        // Nije moguce smanjiti kapacitet vise od trenutnog broja clanova
        List<Attendant> attendants = attendantService.getAllAttendantsOfAGroup(trainingGroup);
        if (trainingGroup.getCapacity() != null && trainingGroup.getCapacity() >= attendants.size()
                && !oldTrainingGroup.getCapacity().equals(trainingGroup.getCapacity())) {
            oldTrainingGroup.setCapacity(trainingGroup.getCapacity());
            changed = true;
        }

        if (changed) {
            trainingGroupService.saveTrainingGroup(oldTrainingGroup);
            redirectAttributes.addFlashAttribute("message", "Grupa izmjenjena.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Krivi unos!");
        }

        return "redirect:/trainer/groups";
    }

    @RequestMapping(value = "/trainer/groups/delete/{id}", method = RequestMethod.POST)
    public String deleteGroup(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        TrainingGroup group = trainingGroupService.getTrainingGroup(id);
        if (group.getAttendants().size() == 0) {
            trainingGroupService.deleteTrainingGroup(group);
        } else {
            redirectAttributes.addFlashAttribute("message", "Nije moguće izbrisati ovu grupu!");
        }

        return "redirect:/trainer/groups";
    }

    @RequestMapping(value = "/trainer/group/{id}/attendants", method = RequestMethod.GET)
    public ModelAndView getListOfAttendants(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("/trainer/group_attendants");

        List<Attendant> attendants = new ArrayList<>();
        if (id != null) {
            attendants = attendantService.getAllAttendantsOfAGroup(trainingGroupService.getTrainingGroup(id));
        }

        modelAndView.addObject("attendants", attendants);
        modelAndView.addObject("groupId", id);
        return modelAndView;
    }

    @RequestMapping(value = "/trainer/group/{groupId}/attendant/{attendantId}", method = RequestMethod.POST)
    public String removeAttendantFromGroup(@PathVariable("groupId") Integer groupId, @PathVariable("attendantId") Integer attendantId) {
        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroup(groupId);
        Attendant attendant = attendantService.getAttendantById(attendantId);

        trainingGroup.getAttendants().remove(attendant);

        trainingGroupService.saveTrainingGroup(trainingGroup);

        return "redirect:" + "/trainer/group/" + groupId + "/attendants";
    }

    private ModelAndView getAddGroupModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView("trainer/add_training_group");

        Trainer trainer = getLoggedTrainer();

        modelAndView.addObject("trainingGroup", new TrainingGroup());
        modelAndView.addObject("trainersSports", trainer.getSports());
        if (message != null) {
            modelAndView.addObject("message", message);
        }
        return modelAndView;
    }

    private Trainer getLoggedTrainer() {
        org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainerService.findTrainerByUsername(loggedUser.getUsername());
    }
}
