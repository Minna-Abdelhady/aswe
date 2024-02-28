package com.example.aswe.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.project.models.User;
import com.example.aswe.project.repositories.UserRepository;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("profile/{userId}")
    public ModelAndView get1User(@PathVariable("userId") Integer userId){
        ModelAndView mav = new ModelAndView("profile.html");
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if(user.getId() == userId){
                mav.addObject("users", users);
                return mav;
            }
        }
        mav.addObject("error", "User not found");
        return mav;
    }

    
}
