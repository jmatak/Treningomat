package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundEntry;
import com.errorsonogsvijeta.treningomat.model.users.User;
import com.errorsonogsvijeta.treningomat.services.PlaygroundEntryService;
import com.errorsonogsvijeta.treningomat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/playground")
public class PlaygroundController {
    @Autowired
    private UserService userService;

    @Autowired
    private PlaygroundEntryService playgroundEntryService;

    @RequestMapping(value = "/newEntry", method = RequestMethod.GET)
    public ModelAndView newEntry() {
        User user = getLoggedUser();

        ModelAndView modelAndView = new ModelAndView("playground/new_entry");
        modelAndView.addObject("entry", new PlaygroundEntry());
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/newEntry", method = RequestMethod.POST)
    public ModelAndView saveEntry(@Valid PlaygroundEntry entry) {
        User loggedUser = getLoggedUser();
        entry.setCreator(loggedUser);
        entry.setCreationTime(new Date());

        playgroundEntryService.saveEntry(entry);
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView allEntries(@Valid PlaygroundEntry entry) {
        List<PlaygroundEntry> allEntries = playgroundEntryService.findAllOrderByCreationTime();

        ModelAndView modelAndView = new ModelAndView("playground/entries");
        modelAndView.addObject("allEntries", allEntries);
        return modelAndView;
    }

    private User getLoggedUser() {
        org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserByUsername(loggedUser.getUsername());
    }
}
