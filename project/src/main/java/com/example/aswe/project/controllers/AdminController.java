package com.example.aswe.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.project.models.Admin;
import com.example.aswe.project.repositories.AdminRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("Dashboard")
    public ModelAndView dashboard() {
        ModelAndView mav = new ModelAndView("/html/admin/adminDashboard.html");
        Admin newAdmin = new Admin();
        mav.addObject("admin", newAdmin);
        return mav;
    }
    
}
