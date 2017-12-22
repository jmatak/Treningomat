package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.training.Sport;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.services.SportService;
import com.errorsonogsvijeta.treningomat.services.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Patrik
 */
@Controller
public class SportController {

    @Autowired
    private SportService sportService;

    @Autowired
    private TrainingGroupService trainingGroupService;

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

    private ModelAndView createAddSportModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView();

        if (!message.equals("")) {
            modelAndView.addObject("message", message);
        }
        modelAndView.addObject("sport", new Sport());
        modelAndView.setViewName("/admin/add_sport");

        return modelAndView;
    }

    @RequestMapping(value = "/sports", method = RequestMethod.GET)
    public ModelAndView showAllSports() {
        ModelAndView modelAndView = new ModelAndView();

        List<Sport> sports = sportService.findAll();
        modelAndView.addObject("allSports", sports);
        modelAndView.setViewName("/sports");

        return modelAndView;
    }


    @RequestMapping(value = "/admin/sports/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteSportWithId(@PathVariable("id") Integer id) {

        try {
            sportService.deleteSport(id);
        } catch (Exception e) {
            //TODO: smisli još što činiti u ovoj situaciji
            //do ovoga ce doci ako se pokusa obrisat sport koji je foreign key nekom drugom entitetu
        }
        //TODO: dodaj jos provjeru jeli admin siguran da zeli obrisati sport(JS)
        return new ModelAndView("redirect:" + "/sports");
    }

    @RequestMapping(value = "/sport/{id}/groups", method = RequestMethod.GET)
    public ModelAndView listOfGroupsForSport(@PathVariable("id") Integer id) {
        Sport sport = sportService.getSport(id);
        List<TrainingGroup> trainingGroups = trainingGroupService.getTrainersBySport(sport);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("groups", trainingGroups);
        modelAndView.addObject("sportName", sport.getName());
        modelAndView.setViewName("sport_groups");

        return modelAndView;
    }

}
