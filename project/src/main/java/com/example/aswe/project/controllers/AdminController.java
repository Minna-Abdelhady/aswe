package com.example.aswe.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.project.models.Admin;
import com.example.aswe.project.models.User;
import com.example.aswe.project.repositories.AdminRepository;
import com.example.aswe.project.repositories.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/Admin")
public class AdminController {
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("Dashboard")
    public ModelAndView dashboard() {
        ModelAndView mav = new ModelAndView("/html/admin/adminDashboard.html");
        Admin newAdmin = new Admin();
        mav.addObject("admin", newAdmin);
        return mav;
    }

    @GetMapping("List-Users")
    public ModelAndView getUsers(){
        ModelAndView mav = new ModelAndView("html/admin/list-users.html");
        List<User> users = this.userRepository.findAll();
        mav.addObject("users", users);
        return mav;
    }

    // @GetMapping("Add-User")
    // public ModelAndView 
    
}
