package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.administration.GroupRequest;
import com.errorsonogsvijeta.treningomat.model.training.Sport;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.services.AttendantService;
import com.errorsonogsvijeta.treningomat.services.GroupRequestService;
import com.errorsonogsvijeta.treningomat.services.SportService;
import com.errorsonogsvijeta.treningomat.services.TrainingGroupService;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SportController {
    @Autowired
    private SportService sportService;

    @Autowired
    private TrainingGroupService trainingGroupService;

    @Autowired
    private GroupRequestService groupRequestService;

    @Autowired
    private AttendantService attendantService;

    @RequestMapping(value = "/sports", method = RequestMethod.GET)
    public ModelAndView showAllSports() {
        ModelAndView modelAndView = new ModelAndView("sports");

        List<Sport> sports = sportService.findAll();

        modelAndView.addObject("sport", new Sport());
        modelAndView.addObject("allSports", sports);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addSport", method = RequestMethod.GET)
    public ModelAndView addSport() {
        return createAddSportModelAndView("");
    }

    @RequestMapping(value = "/admin/addSport", method = RequestMethod.POST)
    public ModelAndView createNewSport(@Valid Sport sport) {
        String message;

        try {
            sportService.saveSport(sport);
            message = "Sport uspješno dodan.";
        } catch (Exception e) {
            message = "Neuspjelo dodavanje sporta!";
        }

        return createAddSportModelAndView(message);
    }

    @RequestMapping(value = "/admin/sports/delete/{id}", method = RequestMethod.POST)
    public String deleteSportWithId(@PathVariable("id") Integer id) {

        try {
            sportService.deleteSport(id);
        } catch (Exception e) {
            //TODO: smisli još što činiti u ovoj situaciji
            //do ovoga ce doci ako se pokusa obrisat sport koji je foreign key nekom drugom entitetu
        }
        return "redirect:/sports";
    }

    @RequestMapping(value = "/sport/{id}/groups", method = RequestMethod.GET)
    public ModelAndView listOfGroupsForSport(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("sport_groups");

        Sport sport = sportService.getSport(id);
        List<TrainingGroup> trainingGroups = trainingGroupService.getTrainersBySport(sport);

        Attendant attendant;
        List<GroupRequest> groupRequests = new ArrayList<>();
        List<TrainingGroup>  attendantGroupsSend = new ArrayList<>();


        try {
            attendant = getLoggedAttendant();
            groupRequests = groupRequestService.getGroupRequestsByAttendantAndToTrainingGroupIn(attendant, trainingGroups);
            for (GroupRequest groupRequest : groupRequests) {
                attendantGroupsSend.add(groupRequest.getToTrainingGroup());
            }

        } catch (Exception ignored){
        }

        modelAndView.addObject("groupsAlreadySend", attendantGroupsSend);
        modelAndView.addObject("allTrainingGroups", trainingGroups);
        modelAndView.addObject("sportName", sport.getName());
        //TODO: trebo bi nekak dodat dali je za neki sport vec poslana prijava, pa ako je onemoguciti klik na gumb za slanje prijave
        return modelAndView;
    }

    private ModelAndView createAddSportModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView();

        if (!message.equals("")) {
            modelAndView.addObject("message", message);
        }
        modelAndView.addObject("sport", new Sport());
        modelAndView.setViewName("/admin/add_sport");

        return modelAndView;
    }

    private Attendant getLoggedAttendant() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendantService.findAttendantByUsername(user.getUsername());
    }

}
