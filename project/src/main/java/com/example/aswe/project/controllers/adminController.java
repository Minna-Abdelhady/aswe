package com.example.aswe.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.models.Admin;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.UserType;
import com.example.aswe.project.models.productDto;
import com.example.aswe.project.models.Product;
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

    public adminController() {

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

    
    // CRUD Products with images

    @Autowired
    private productRepository productService;

    @GetMapping("products")
    public ModelAndView showAllproducts() {
        ModelAndView mav = new ModelAndView("products/index");
        List<Product> products = productService.findAll(Sort.by(Sort.Direction.DESC, "id"));
        mav.addObject("products", products);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createProduct() {
        ModelAndView mav = new ModelAndView("products/createProduct");
        productDto ProductDto = new productDto();
        mav.addObject("ProductDto", ProductDto);
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView saveProduct(@Valid @ModelAttribute productDto productDto, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("products/createProduct");
            return mav;
        }

        // Check if image file is present
        MultipartFile image = productDto.getImagFile();
        if (image == null || image.isEmpty()) {
            result.addError(new FieldError("productDto", "imagFile", "Photo is required"));
            mav.setViewName("products/createProduct");
            return mav;
        }

        // Generate storage file name
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "-" + StringUtils.cleanPath(image.getOriginalFilename());

        // Define upload directory
        String uploadDir = "static/";

        // Create upload path
        Path uploadPath = Paths.get(uploadDir);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the image file
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            result.addError(new FieldError("productDto", "imagFile", "Error uploading file"));
            mav.setViewName("products/createProduct");
            return mav;
        }

        // Create and save product
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImgFileName(storageFileName);
        productService.save(product);

        mav.setViewName("redirect:/products");
        return mav;
    }

    @GetMapping("/edit")
    public ModelAndView edit(@RequestParam int id) {
        ModelAndView mav = new ModelAndView("products/editproduct");
        Product product = productService.findById(id).get();
        mav.addObject("product", product);
        productDto ProductDto = new productDto();
        ProductDto.setName(product.getName());
        ProductDto.setBrand(product.getBrand());
        ProductDto.setCategory(product.getCategory());
        ProductDto.setPrice(product.getPrice());
        ProductDto.setDescription(product.getDescription());
        mav.addObject("ProductDto", ProductDto);
        return mav;
    }

    @PostMapping("/edit")
    public ModelAndView updateProduct(@RequestParam int id, @Valid @ModelAttribute productDto ProductDto, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        try {
            Product product = productService.findById(id).get();
            mav.addObject("product", product);

            if (result.hasErrors()) {
                mav.setViewName("products/EditProduct");
                return mav;
            }
            if (!ProductDto.getImagFile().isEmpty()) {
                // Delete old image
                String uploadDir = "static/";
                Path oldImagePath = Paths.get(uploadDir + product.getImgFileName());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }
            }
            // Save new image file
            MultipartFile image = ProductDto.getImagFile();
            Date createdAt = new Date();
            String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get("static/" + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                product.setImgFileName(storageFileName);
            }

            // Update the product with new data from the ProductDto
            product.setName(ProductDto.getName());
            product.setDescription(ProductDto.getDescription());
            product.setCategory(ProductDto.getCategory());
            product.setBrand(ProductDto.getBrand());
            product.setPrice(ProductDto.getPrice());

            // Save the updated product
            productService.save(product);

            mav.setViewName("redirect:/products");
            return mav;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            mav.setViewName("redirect:/products");
            return mav;
        }
    }

    @GetMapping("/delete")
    public ModelAndView deleteproduct(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        try {
            Product prod = productService.findById(id).get();
            Path imagePath = Paths.get("static/" + prod.getImgFileName());
            Files.delete(imagePath);
            productService.deleteById(id);
            mav.setViewName("redirect:/products");
            return mav;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            mav.setViewName("redirect:/products");
            return mav;
        }
    }
}
