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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * TODO: opis
 *
 * @author Matej Pipalović
 * @version 1.0
 */
@Controller
public class UserController {
    // todo dodati lokalizirane poruke...
    // todo, sve ove "errore" se moze ispitati i preko jQuerrya

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
            return error("username vec postoji");
        }

        // todo, sta ako nema slike
        String fileName = Util.writeToFile(attendant.getFile(), request, "users", "user_" + attendant.getUsername());
        if (fileName == null) {
            // todo neuspjesno dodavanje(slika se ne moze spremiti), neka prikladnija poruka mozda?!
            return error("pogreska, molimo pokusajte kasnije");
        }

        registrationService.saveAttendant(attendant, fileName);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        return mav;
    }

    private ModelAndView error(String message) {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    private ModelAndView getModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("allCities", cityService.findAll());
        modelAndView.addObject("attendant", new Attendant());
        return modelAndView;
    }
}
