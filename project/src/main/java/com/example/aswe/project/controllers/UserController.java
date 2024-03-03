package com.example.aswe.project.controllers;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("")
    public ModelAndView getUsers(){
        ModelAndView mav=new ModelAndView("list-users.html");
        List<User> users=this.userRepository.findAll();
        mav.addObject("users",users);
        return mav;
    }
    @GetMapping("Registration")
    public ModelAndView addUser(){
        ModelAndView mav=new ModelAndView("registartion.html");
        User newUser=new User();
        mav.addObject("user",newUser);
        return mav;

    }
    @PostMapping("Registration")
    public String saveFruit(@ModelAttribute User user){
        String encoddedPassword=BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(12));
        user.setPassword(encoddedPassword);
        this.userRepository.save(user);
        return "Added";
    }

    @GetMapping("profile/{userId}")
    public ModelAndView get1User(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView("/html/user/view-profile.html");
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getId() == userId) {
                mav.addObject("user", user);
                return mav;
            }
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

    /*
     * @GetMapping("profile/{userId}")
     * public ResponseEntity get1User(@PathVariable("userId") Integer userId){
     * List<User> users = this.userRepository.findAll();
     * for (User user : users) {
     * if(user.getId() == userId){
     * return new ResponseEntity<>(user, HttpStatus.OK);
     * }
     * }
     * return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
     * }
     */

    @GetMapping("edit-profile/{userId}")
    public ModelAndView editProfile(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView("/html/user/edit-profile.html");
        // User newUser = this.userRepository.findByUserID(userId);
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getId() == userId) {
                User newUser = user;
                mav.addObject("user", newUser);
                return mav;
            }
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
        // mav.addObject("user", newUser);
        // return mav;
    }

    // @PostMapping("edit-profile/{userId}")
    // public ModelAndView saveEditedUser(@PathVariable("userId") int userId,
    // @ModelAttribute User editedUser){
    // List<User> users = this.userRepository.findAll();
    // User newUser = new User();
    // for (User user : users) {
    // if(user.getId() == userId){
    // newUser = user;
    // break;
    // }
    // }
    // newUser.setFName(editedUser.getFName());
    // newUser.setLName(editedUser.getLName());
    // newUser.setEmail(editedUser.getEmail());
    // String encoddedPassword = BCrypt.hashpw(editedUser.getPassword(),
    // BCrypt.gensalt(12));
    // newUser.setPassword(encoddedPassword);
    // this.userRepository.save(newUser);
    // ModelAndView mav = new ModelAndView("/html/user/view-profile.html");
    // mav.addObject("user", newUser);
    // return mav;

    // }

    @PostMapping("edit-profile/{userId}")
    public String saveEditedUser(@PathVariable("userId") int userId, @ModelAttribute User editedUser) {
        List<User> users = this.userRepository.findAll();
        User user = new User();
        for (User user1 : users) {
            if (user1.getId() == userId) {
                user = user1;
                break;
            }
        }
        user.setFName(editedUser.getFName());
        user.setLName(editedUser.getLName());
        user.setEmail(editedUser.getEmail());
        String encoddedPassword = BCrypt.hashpw(editedUser.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encoddedPassword);
        this.userRepository.save(user);
        return "Edited successfully";
    }
    
}
