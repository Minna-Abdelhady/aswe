package com.example.aswe.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.demo.models.User;
import com.example.aswe.demo.services.UserService;

@RestController
@RequestMapping("/User")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ModelAndView getUsers() {
        ModelAndView mav = new ModelAndView("/html/user/list-users.html");
        List<User> users = userService.findAll();
        mav.addObject("users", users);
        return mav;
    }

    @GetMapping("/Registration")
    public ModelAndView addUser() {
        ModelAndView mav = new ModelAndView("/html/user/registration.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("/Registration")
    public RedirectView saveUser(@Validated @ModelAttribute User user) {
        userService.save(user);
        return new RedirectView("/User/Home");
    }

    @GetMapping("/profile/{userId}")
    public ModelAndView getUser(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView("/html/user/view-profile.html");
        User user = userService.findById(userId);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/edit-profile/{userId}")
    public RedirectView saveProfile(@PathVariable("userId") int userId, @ModelAttribute User updatedUser) {
        userService.update(userId, updatedUser);
        return new RedirectView("/User/profile/" + userId);
    }

    @GetMapping("/delete-account/{userId}")
    public RedirectView deleteAccount(@PathVariable("userId") int userId) {
        userService.delete(userId);
        return new RedirectView("/User/Registration");
    }
    
}
