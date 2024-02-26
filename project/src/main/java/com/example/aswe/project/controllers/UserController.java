package com.example.aswe.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aswe.project.repositories.UserRepository;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    
}
