package com.example.aswe.project.controllers;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.aswe.project.models.User;
import com.example.aswe.project.models.UserFeedback;
import com.example.aswe.project.models.UserType;
import com.example.aswe.project.models.Product;
import com.example.aswe.project.repositories.FeedbackRepository;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.productRepository;
import com.example.aswe.project.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private productRepository ProductRepository;

    public UserController(productRepository ProductRepository) {
        this.ProductRepository = ProductRepository; 
    }

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository; 
    }

    public UserController() {}

    public void setProductRepository(productRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }
    
    @GetMapping("")
    public ModelAndView getUsers() {
        ModelAndView mav = new ModelAndView("/html/user/list-users.html");
        List<User> users = this.userRepository.findAll();
        mav.addObject("users", users);
        return mav;
    }

    @GetMapping("Home")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("/html/user/index.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @GetMapping("about")
    public ModelAndView about() {
        ModelAndView mav = new ModelAndView("/html/user/about.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @GetMapping("about-us")
    public ModelAndView aboutus() {
        ModelAndView mav = new ModelAndView("/html/user/about.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    // @GetMapping("Homepage")
    // public ModelAndView homepage(@PathVariable("userId") Integer userId){
    //     ModelAndView mav = new ModelAndView("/html/user/index.html");
    //     User user = this.userRepository.findByid(userId);
    //     mav.addObject("user", user);
    //     return mav;
    // }

    @GetMapping("/Registration")
    public ModelAndView addUser() {
        ModelAndView mav = new ModelAndView("/html/user/registration.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("/Registration")
    public RedirectView saveUser(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("/User/Registration");
        }
        String encoddedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encoddedPassword);
        // user.setType();
        UserType userType = new UserType();
        userType.setId(2); // id 2 corresponds to User type
        userType.setName(UserType.TYPE_USER);
        user.setType(userType);
        userRepository.save(user);
        return new RedirectView("/User/Home");
    }

    @GetMapping("/Login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("/html/user/login.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("/Login")
    public RedirectView loginProcess(@RequestParam("Email") @NotBlank(message = "Email is required") @Email(message = "Email must be a valid email address") String Email,
                                  @RequestParam("Password") @NotBlank(message = "Password is required") String Password,
                                  HttpSession session) {
        System.out.println("Login");
        // User dbUser = userRepository.findByEmail(Email);
        List<User> users = this.userRepository.findAll();
        User dbUser=new User();
        for (User user : users) {
            if (user.getEmail().equals(Email)) {
                dbUser = user;
                break;
            }
        }
        // if (dbUser == null) {
        //     // User not found
        //     return new RedirectView("/User/Login");
        // }
        if (dbUser.getType().getId() == 1) {
            return new RedirectView("/Admin/Dashboard");
        }
        Boolean isPasswordMatched = BCrypt.checkpw(Password, dbUser.getPassword());
        if (isPasswordMatched) {
            session.setAttribute(Email, dbUser.getEmail());
            return new RedirectView("/User/Home");
        } else {
            // Working password wrong
            return new RedirectView("/User/Login");
        }
    }

    @GetMapping("List-products")
    public ModelAndView getproducts(){
        ModelAndView mav = new ModelAndView("html/user/list-products.html");
        List<Product> Products = this.ProductRepository.findAll();
        mav.addObject("products",Products);
        return mav;
    }

    // @GetMapping("List-products")
    // public ResponseEntity getproducts(){
    //     List<product> Products = this.ProductRepository.findAll();
    //     return new ResponseEntity<>(Products, HttpStatus.OK);
    // }

    // @GetMapping("Registration")
    // public ModelAndView addUser() {
    //     ModelAndView mav = new ModelAndView("/html/user/registration.html");
    //     User newUser = new User();
    //     mav.addObject("user", newUser);
    //     return mav;
    // }

    // @PostMapping("Registration")
    // public String saveUser(@ModelAttribute User user) {
    //     String encoddedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
    //     user.setPassword(encoddedPassword);
    //     this.userRepository.save(user);
    //     return "Added";
    // }

    // @GetMapping("Login")
    // public ModelAndView login() {
    //     ModelAndView mav = new ModelAndView("/html/user/login.html");
    //     User newUser = new User();
    //     mav.addObject("user", newUser);
    //     return mav;
    // }

    // @PostMapping("Login")
    // public RedirectView loginProcess(@RequestParam("Email") String Email, @RequestParam("Password") String Password,
    //         HttpSession session) {
    //     System.out.println("Login");
    //     // User dbUser = this.userRepository.findByEmail(Email);
    //     User dbUser = new User();
    //     List<User> users = this.userRepository.findAll();
    //     for (User user : users) {
    //         if (user.getEmail().equals(Email)) {
    //             dbUser = user;
    //             break;
    //         }
    //     }
    //     System.out.println("Login");
    //     System.out.println(dbUser.getEmail());

    //     Boolean isPasswordMatched = BCrypt.checkpw(Password, dbUser.getPassword());
    //     if (isPasswordMatched) {
    //         session.setAttribute(Email, dbUser.getEmail());
    //         return new RedirectView("/User/Home");
    //     } else {
    //         // Working password wrong
    //         return new RedirectView("/User/Login");
    //     }
    //     // return new RedirectView("/User/Home");
    // }
    

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

    @PostMapping("/feedback")
    public String addFeedback(@ModelAttribute UserFeedback feedback) {
        this.feedbackRepository.save(feedback); // save the new feedback record
        return "Feedback saved successfully";
    }

    public class HomeController {
        @Autowired
        private ProductService productService;

        @GetMapping("/")
        public String home(Model model) {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            return "home";
        }
    }

    @GetMapping("/store")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("store.html"); 
        return modelAndView;
    }

}
