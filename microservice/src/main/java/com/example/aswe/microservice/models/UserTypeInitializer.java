package com.example.aswe.microservice.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.aswe.microservice.repositories.UserTypeRepository;

@Component
public class UserTypeInitializer implements CommandLineRunner {

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeUserTypes();
    }

    private void initializeUserTypes(){
        // Check if the types exists
        // if not, insert them
        if(userTypeRepository.findByname(UserType.TYPE_ADMIN) == null) {
            UserType adminType = new UserType();
            adminType.setName(UserType.TYPE_ADMIN);
            userTypeRepository.save(adminType);
        }

        if(userTypeRepository.findByname(UserType.TYPE_USER) == null){
            UserType userType = new UserType();
            userType.setName(UserType.TYPE_USER);
            userTypeRepository.save(userType);
        }
    }
}