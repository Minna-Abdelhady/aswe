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
import com.example.aswe.project.models.UserType;
import com.example.aswe.project.models.Product;
// import com.example.aswe.project.repositories.adminRepository;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.adminRepository;
import com.example.aswe.project.repositories.productRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/Admin")
public class adminController {

    @Autowired
    private adminRepository adminRepo;

    public adminController(adminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    public adminController(){

    }

    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RedirectView saveUserTest(User user) {
        if (userRepository != null) {
            userRepository.save(user);
        }
        return new RedirectView("/Admin/List-Users");
    }

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

    // Admin profile management

    @GetMapping("Profile/{adminId}")
    public ModelAndView getAdmin(@PathVariable("adminId") Integer adminId) {
        ModelAndView mav = new ModelAndView("/html/admin/view-profile.html");
        User admin = this.userRepository.findByid(adminId);
        if (admin != null) {
            mav.addObject("admin", admin);
            return mav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

    // Modified getAdmin method to use sessions 
    @GetMapping("Profile")
    public ModelAndView getAdminProfile(HttpSession session) {
        // String email = (String) session.getAttribute("Email");
        Integer adminId = (Integer) session.getAttribute("id");
        if (adminId == null) {
            ModelAndView errorMav = new ModelAndView("error.html");
            errorMav.addObject("errorMessage", "User not found");
            return errorMav;
        }
        User admin = this.userRepository.findByid(adminId);
        if (admin != null) {
            ModelAndView mav = new ModelAndView("/html/admin/view-profile.html");
            mav.addObject("admin", admin);
            return mav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

    // User CRUD

    @GetMapping("Add-User")
    public ModelAndView addUser() {
        ModelAndView mav = new ModelAndView("/html/admin/add-user.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("Save-User")
    public RedirectView saveUser(@ModelAttribute User user) {
        // Setting the user type
        UserType userType = new UserType();
        userType.setId(2); // id 2 corresponds to User type
        userType.setName(UserType.TYPE_USER);
        user.setType(userType);
        this.userRepository.save(user);
        return new RedirectView("/Admin/List-Users");
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

    @GetMapping("delete-userAccount/{userId}")
    public RedirectView deleteUserAccount(@PathVariable("userId") int userId) {
        User user = this.userRepository.findByid(userId);
        if (user != null) {
            this.userRepository.delete(user);
            return new RedirectView("/Admin/List-Users");
        }
        return new RedirectView("/Admin/List-Users");
    }

    // Admin CRUD

    @GetMapping("Add-Admin")
    public ModelAndView addAdmin() {
        ModelAndView mav = new ModelAndView("/html/admin/add-admin.html");
        User newAdmin = new User();
        mav.addObject("admin", newAdmin);
        return mav;
    }

    @PostMapping("Save-Admin")
    public RedirectView saveAdmin(@ModelAttribute User admin) {
        // Setting the user type
        UserType userType = new UserType();
        userType.setId(1); // id 1 corresponds to Admin type
        userType.setName(UserType.TYPE_USER);
        admin.setType(userType);
        this.userRepository.save(admin);
        return new RedirectView("/Admin/List-Admins");
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

    @GetMapping("edit-admin/{adminId}")
    public ModelAndView editAdminProfile(@PathVariable("adminId") Integer adminId) {
        ModelAndView mav = new ModelAndView("/html/admin/edit-admin.html");
        User newAdmin = this.userRepository.findByid(adminId);
        if (newAdmin != null) {
            mav.addObject("admin", newAdmin);
            return mav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }

    @PostMapping("edit-admin/{adminId}")
    public RedirectView saveAdminProfile(@PathVariable("adminId") int adminId, @ModelAttribute User updatedAdmin) {
        User user = this.userRepository.findByid(adminId);
        if (!user.getFName().equals(updatedAdmin.getFName())) {
            user.setFName(updatedAdmin.getFName());
        }
        if (!user.getLName().equals(updatedAdmin.getLName())) {
            user.setLName(updatedAdmin.getLName());
        }
        if (!user.getEmail().equals(updatedAdmin.getEmail())) {
            user.setEmail(updatedAdmin.getEmail());
        }
        if (!user.getPassword().equals(updatedAdmin.getPassword())) {
            String encoddedPassword = BCrypt.hashpw(updatedAdmin.getPassword(), BCrypt.gensalt(12));
            user.setPassword(encoddedPassword);
        }
        this.userRepository.save(user);
        return new RedirectView("/Admin/List-Admins");
    }

    @GetMapping("delete-adminAccount/{adminId}")
    public RedirectView deleteAdminAccount(@PathVariable("adminId") int adminId) {
        User admin = this.userRepository.findByid(adminId);
        if (admin != null) {
            this.userRepository.delete(admin);
            return new RedirectView("/Admin/List-Admins");
        }
        return new RedirectView("/Admin/List-Admins");
    }

    // Products CRUD

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
        return new RedirectView("/Admin/List-products");
    }

    @GetMapping("List-products")
    public ModelAndView getproducts() {
        ModelAndView mav = new ModelAndView("html/admin/list-products.html");
        List<Product> Products = this.ProductRepository.findAll();
        mav.addObject("products", Products);
        return mav;
    }

    @GetMapping("edit-product/{productId}")
    public ModelAndView editProduct(@PathVariable("productId") Integer productId) {
        ModelAndView mav = new ModelAndView("/html/admin/edit-product.html");
        Product newProduct = this.ProductRepository.findByid(productId);
        if (newProduct != null) {
            mav.addObject("product", newProduct);
            return mav;
        }
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "Product not found");
        return errorMav;
    }

    @PostMapping("edit-product/{productId}")
    public RedirectView saveProduct(@PathVariable("productId") int productId, @ModelAttribute Product updatedProduct) {
        Product product = this.ProductRepository.findByid(productId);
        if (!product.getName().equals(updatedProduct.getName())) {
            product.setName(updatedProduct.getName());
        }
        if (!product.getBrand().equals(updatedProduct.getBrand())) {
            product.setBrand(updatedProduct.getBrand());
        }
        if (!product.getCategory().equals(updatedProduct.getCategory())) {
            product.setCategory(updatedProduct.getCategory());
        }
        if (product.getPrice() != updatedProduct.getPrice()) {
            product.setPrice(updatedProduct.getPrice());
        }
        if (!product.getDescription().equals(updatedProduct.getDescription())) {
            product.setDescription(updatedProduct.getDescription());
        }
        this.ProductRepository.save(product);
        return new RedirectView("/Admin/List-products");
    }

    @GetMapping("delete-product/{productId}")
    public RedirectView deleteAccount(@PathVariable("productId") int ProductId) {
        Product Product = this.ProductRepository.findByid(ProductId);
        if (ProductId != 0) {
            this.ProductRepository.delete(Product);
            return new RedirectView("/Admin/List-products");
        }
        return new RedirectView("/Admin/List-products");
    }

}
