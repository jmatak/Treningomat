package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.repository.AttendantRepository;
import com.errorsonogsvijeta.treningomat.services.CityService;
import com.errorsonogsvijeta.treningomat.services.RegistrationService;
import com.errorsonogsvijeta.treningomat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private AttendantRepository attendantRepository;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUser(@Valid Attendant attendant, BindingResult result, HttpServletRequest request, RedirectAttributes attributes) {
        if (userService.findUserByUsername(attendant.getUsername()) != null) {
            attributes.addFlashAttribute("message", "Username vec postoji");
            return "redirect:/home";
        }

        MultipartFile file = attendant.getFile();
        String fileName = "attendant_" + attendant.getUsername() + Util.getExtension(file.getOriginalFilename());
        String subdir = "attendants";
        String msg = Util.writeToFile(file, subdir, fileName, request);
        if (msg != null) {
            attributes.addFlashAttribute("message", msg);
            return "redirect:/home";
        }

        registrationService.saveAttendant(attendant, fileName);

        return "redirect:/home";
    }
}
