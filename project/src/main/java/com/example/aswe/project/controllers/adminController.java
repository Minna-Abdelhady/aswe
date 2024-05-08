package com.example.aswe.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.models.Admin;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.Product;
// import com.example.aswe.project.repositories.adminRepository;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.productRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/Admin")
public class adminController {

    // @Autowired
    // private adminRepository AdminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private productRepository ProductRepository;

    @GetMapping("Dashboard")
    public ModelAndView dashboard() {
        ModelAndView mav = new ModelAndView("/html/admin/adminDashboard.html");
        Admin newAdmin = new Admin();
        mav.addObject("admin", newAdmin);
        return mav;
    }

    @GetMapping("Reports")
    public ModelAndView reports() {
        ModelAndView mav = new ModelAndView("/html/admin/reports.html");
        Admin newAdmin = new Admin();
        mav.addObject("admin", newAdmin);
        return mav;
    }

    @GetMapping("List-Users")
    public ModelAndView getUsers() {
        ModelAndView mav = new ModelAndView("html/admin/list-users.html");
        List<User> persons = this.userRepository.findAll();
        List<User> users = new ArrayList<>();

        for (User person : persons) {
            if (person.getType().getId() == 2) {
                users.add(person);
            }
        }
        mav.addObject("users", users);
        return mav;
    }

    @GetMapping("List-Admins")
    public ModelAndView getAdmins() {
        ModelAndView mav = new ModelAndView("html/admin/list-admins.html");
        List<User> persons = this.userRepository.findAll();
        List<User> admins = new ArrayList<>();

        for (User person : persons) {
            if (person.getType().getId() == 1) {
                admins.add(person);
            }
        }
        mav.addObject("admins", admins);
        return mav;
    }

    @GetMapping("edit-user/{userId}")
    public ModelAndView editProfile(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView("/html/admin/edit-user.html");
        User newUser = this.userRepository.findByid(userId);
        if (newUser != null) {
            mav.addObject("user", newUser);
            return mav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

    @PostMapping("edit-user/{userId}")
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
        return new RedirectView("/Admin/List-Users");
    }

    @GetMapping("List-products")
    public ModelAndView getproducts() {
        ModelAndView mav = new ModelAndView("html/admin/list-products.html");
        List<Product> Products = this.ProductRepository.findAll();
        mav.addObject("products", Products);
        return mav;
    }

    @GetMapping("add-product")
    public ModelAndView addproduct() {
        ModelAndView mav = new ModelAndView("html/admin/AddProduct.html");
        Product newProduct = new Product();
        mav.addObject("Product", newProduct);
        return mav;
    }

    @PostMapping("add-product")
    public RedirectView saveproduct(@ModelAttribute Product newProduct) {
        this.ProductRepository.save(newProduct);
        // System.out.println("Fone");
        return new RedirectView("/Admin/List-products");
    }

    /*
     * @PostMapping("Registration")
     * public String saveUser(@ModelAttribute User user) {
     * this.userRepository.save(user);
     * return "Added";
     * }
     */

    @GetMapping("delete-product/{productId}")
    public ModelAndView deleteAccount(@PathVariable("productId") int ProductId) {
        Product Product = this.ProductRepository.findByProductId(ProductId);
        if (ProductId != 0) {
            this.ProductRepository.delete(Product);
            ModelAndView errorMav = new ModelAndView("error.html");
            errorMav.addObject("errorMessage", "Deleted successfully");
            return errorMav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

}
