package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.calendar.Term;
import com.errorsonogsvijeta.treningomat.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private TrainingService trainingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getCalendar() {
        ModelAndView modelAndView = new ModelAndView("calendar");

        List<Term> terms = Term.toTerms(trainingService.findAll());
        modelAndView.addObject("events", terms);

        return modelAndView;
    }
}
