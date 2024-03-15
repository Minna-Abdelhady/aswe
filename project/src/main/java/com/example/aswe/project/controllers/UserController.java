package com.example.aswe.project.controllers;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.models.User;
import com.example.aswe.project.models.UserFeedback;
import com.example.aswe.project.repositories.FeedbackRepository;
import com.example.aswe.project.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // NOT HERE (IN THE ADMIN CONTROLLER)
    @GetMapping("")
    public ModelAndView getUsers() {
        ModelAndView mav = new ModelAndView("/html/user/list-users.html");
        List<User> users = this.userRepository.findAll();
        mav.addObject("users", users);
        return mav;
    }

    @GetMapping("Registration")
    public ModelAndView addUser() {
        ModelAndView mav = new ModelAndView("registartion.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("Registration")
    public String saveUser(@ModelAttribute User user) {
        String encoddedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encoddedPassword);
        this.userRepository.save(user);
        return "Added";
    }

    @GetMapping("Login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("/html/user/login.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("Login")
    public String loginProcess(@RequestParam("Email") String Email, @RequestParam("Password") String Password) {
        // User dbUser = this.userRepository.findByEmail(Email);
        User dbUser = new User();
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getEmail() == Email) {
                dbUser = user;
                break;
            }
        }
        Boolean isPasswordMatched = BCrypt.checkpw(Password, dbUser.getPassword());
        if (isPasswordMatched) {
            return "Welcome " + dbUser.getFName();
        } else {
            return "Failed to login";
        }
    }

    @GetMapping("profile/{userId}")
    public ModelAndView get1User(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView("/html/user/view-profile.html");
        User user = this.userRepository.findByid(userId);
        if (user != null) {
            mav.addObject("user", user);
            return mav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

    @GetMapping("edit-profile/{userId}")
    public ModelAndView editProfile(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView("/html/user/edit-profile.html");
        User newUser = this.userRepository.findByid(userId);
        if (newUser != null) {
            mav.addObject("user", newUser);
            return mav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

    @PostMapping("edit-profile/{userId}")
    public RedirectView saveProfile(@PathVariable("userId") int userId, @ModelAttribute User updatedUser) {
        User user = this.userRepository.findByid(userId);
        if (!user.getFName().equals(updatedUser.getFName())) {
            user.setFName(updatedUser.getFName());
        }
        if (!user.getLName().equals(updatedUser.getLName())) {
            user.setLName(updatedUser.getLName());
        }
        if (!user.getEmail().equals(updatedUser.getEmail())) {
            user.setEmail(updatedUser.getEmail());
        }
        if (!user.getPassword().equals(updatedUser.getPassword())) {
            String encoddedPassword = BCrypt.hashpw(updatedUser.getPassword(), BCrypt.gensalt(12));
            user.setPassword(encoddedPassword);
        }
        this.userRepository.save(user);
        return new RedirectView("/User/profile/" + userId);
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("delete-account/{userId}")
    public ModelAndView deleteAccount(@PathVariable("userId") int userId) {
        User user = this.userRepository.findByid(userId);
        if (user != null) {
            this.userRepository.delete(user);
            ModelAndView errorMav = new ModelAndView("error.html");
            errorMav.addObject("errorMessage", "Deleted successfully");
            return errorMav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

    // --------------------------------------------------------------------------------------------------//
    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/addfeedback")
    public ModelAndView addFeedback() {
        ModelAndView mav = new ModelAndView("feedback.html");
        UserFeedback newUserFeedback = new UserFeedback();
        mav.addObject("feedback", newUserFeedback);
        return mav;
    }

    @SuppressWarnings("null")
    @PostMapping("/feedback")
    public String addFeedbackP(@ModelAttribute UserFeedback feedback) {
        this.feedbackRepository.save(feedback); // save the new feedback record
        return "Feedback saved successfully";
    }

}
