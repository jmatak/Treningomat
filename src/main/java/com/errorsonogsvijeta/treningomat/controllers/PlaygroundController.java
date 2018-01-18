package com.errorsonogsvijeta.treningomat.controllers;

import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundComment;
import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundEntry;
import com.errorsonogsvijeta.treningomat.model.users.User;
import com.errorsonogsvijeta.treningomat.services.PlaygroundEntryService;
import com.errorsonogsvijeta.treningomat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView allEntries() {
        ModelAndView modelAndView = new ModelAndView("playground/entries");

        List<PlaygroundEntry> allEntries = playgroundEntryService.findAll();


        modelAndView.addObject("entry", new PlaygroundEntry());
        modelAndView.addObject("allEntries", allEntries);
        modelAndView.addObject("comment", new PlaygroundComment());
        return modelAndView;
    }

    @RequestMapping(value = "/newEntry", method = RequestMethod.GET)
    public ModelAndView newEntry() {
        ModelAndView modelAndView = new ModelAndView("playground/new_entry");

        User user = getLoggedUser();

        modelAndView.addObject("entry", new PlaygroundEntry());
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/newEntry", method = RequestMethod.POST)
    public String saveEntry(@Valid PlaygroundEntry entry) {
        User loggedUser = getLoggedUser();
        entry.setCreator(loggedUser);
        entry.setCreationTime(new Date());

        playgroundEntryService.saveEntry(entry);
        return "redirect:/playground";
    }

    @RequestMapping(value = "/entry/{id}", method = RequestMethod.GET)
    public ModelAndView viewEntry(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("playground/entry");

        PlaygroundEntry entry = playgroundEntryService.findPlaygroundEntryById(id);
        if (entry == null) {
            //TODO obradi pogrešku
        }

        modelAndView.addObject("entry", entry);
        modelAndView.addObject("comment", new PlaygroundComment());
        return modelAndView;
    }

    @RequestMapping(value = "/entry/{id}", method = RequestMethod.POST)
    public String addComment(@PathVariable("id") Integer id, @Valid PlaygroundComment comment) {
        PlaygroundEntry entry = playgroundEntryService.findPlaygroundEntryById(id);
        if (entry == null) {
            //TODO obradi pogrešku
        }
        User loggedUser = getLoggedUser();

        comment.setCreator(loggedUser);
        comment.setCreationTime(new Date());
        playgroundEntryService.addComment(entry, comment);

        return "redirect:/playground";
    }

    @RequestMapping(value = "/comments/delete/{id}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        System.out.println("Usa san u metodu");
        PlaygroundComment playgroundComment = playgroundEntryService.findPlaygroundCommentById(id);
        playgroundEntryService.deleteComment(playgroundComment);
        return "redirect:/playground";
    }

    private User getLoggedUser() {
        org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserByUsername(loggedUser.getUsername());
    }
}
