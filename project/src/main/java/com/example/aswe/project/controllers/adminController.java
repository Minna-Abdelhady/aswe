package com.example.aswe.project.controllers;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.models.Admin;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.product;
import com.example.aswe.project.repositories.AdminRepository;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.productRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/Admin")
public class AdminController {
    
    @Autowired
    private AdminRepository AdminRepository;

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

    @GetMapping("List-Users")
    public ModelAndView getUsers(){
        ModelAndView mav = new ModelAndView("html/admin/list-users.html");
        List<User> users = this.userRepository.findAll();
        mav.addObject("users", users);
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
    public ModelAndView getproducts(){
        ModelAndView mav = new ModelAndView("html/admin/list-products.html");
        List<product> Products = this.ProductRepository.findAll();
        mav.addObject("products",Products);
        return mav;
    }

@GetMapping("add-product")
public ModelAndView addproduct(){
    ModelAndView mav=new ModelAndView("html/admin/AddProduct.html");
    product newProduct= new product();
    mav.addObject("Product",newProduct);
    return mav;
}
@PostMapping("add-product")
public String saveproduct(@ModelAttribute product newProduct){
   this.ProductRepository.save(newProduct);
   return "Product Added";
}

@GetMapping("delete-product/{productId}")
public ModelAndView deleteAccount(@PathVariable("productId") int ProductId) {
    product Product = this.ProductRepository.findByProductId(ProductId);
    if (ProductId !=0) {
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
