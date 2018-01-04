package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.repository.AttendantRepository;
import com.errorsonogsvijeta.treningomat.services.CityService;
import com.errorsonogsvijeta.treningomat.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private CityService cityService;
    @Autowired
    private AttendantRepository attendantRepository;

    @RequestMapping(value = {"/registration"}, method = RequestMethod.GET)
    public ModelAndView register() {
        return getModelAndView();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid Attendant attendant, BindingResult result, HttpServletRequest request) {
        if (attendantRepository.findAttendantByUsername(attendant.getUsername()) != null) {
            return error("Username vec postoji");
        }

        MultipartFile file = attendant.getFile();
        String fileName = "attendant_" + attendant.getUsername();
        String subdir = "attendants";
        String msg = Util.writeToFile(file, subdir, fileName, request);
        if (msg != null) {
            return error(msg);
        }

        registrationService.saveAttendant(attendant, fileName);

        return new ModelAndView("home");
    }

    private ModelAndView error(String message) {
        ModelAndView modelAndView = getModelAndView();
        
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    private ModelAndView getModelAndView() {
        ModelAndView modelAndView = new ModelAndView("registration");

        modelAndView.addObject("allCities", cityService.findAll());
        modelAndView.addObject("attendant", new Attendant());
        return modelAndView;
    }
}
