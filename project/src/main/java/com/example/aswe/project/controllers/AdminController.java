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
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
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

    // Products CRUD

    // @GetMapping("add-product")
    // public ModelAndView addproduct() {
    // ModelAndView mav = new ModelAndView("html/admin/AddProduct.html");
    // Product newProduct = new Product();
    // mav.addObject("Product", newProduct);
    // return mav;
    // }

    // @PostMapping("add-product")
    // public RedirectView saveproduct(@ModelAttribute Product newProduct) {
    // this.ProductRepository.save(newProduct);
    // return new RedirectView("/Admin/List-products");
    // }

    // @GetMapping("List-products")
    // public ModelAndView getproducts() {
    // ModelAndView mav = new ModelAndView("html/admin/list-products.html");
    // List<Product> Products = this.ProductRepository.findAll();
    // mav.addObject("products", Products);
    // return mav;
    // }

    // @GetMapping("edit-product/{productId}")
    // public ModelAndView editProduct(@PathVariable("productId") Integer productId)
    // {
    // ModelAndView mav = new ModelAndView("/html/admin/edit-product.html");
    // Product newProduct = this.ProductRepository.findByid(productId);
    // if (newProduct != null) {
    // mav.addObject("product", newProduct);
    // return mav;
    // }
    // ModelAndView errorMav = new ModelAndView("error.html");
    // errorMav.addObject("errorMessage", "Product not found");
    // return errorMav;
    // }

    // @PostMapping("edit-product/{productId}")
    // public RedirectView saveProduct(@PathVariable("productId") int productId,
    // @ModelAttribute Product updatedProduct) {
    // Product product = this.ProductRepository.findByid(productId);
    // if (!product.getName().equals(updatedProduct.getName())) {
    // product.setName(updatedProduct.getName());
    // }
    // if (!product.getBrand().equals(updatedProduct.getBrand())) {
    // product.setBrand(updatedProduct.getBrand());
    // }
    // if (!product.getCategory().equals(updatedProduct.getCategory())) {
    // product.setCategory(updatedProduct.getCategory());
    // }
    // if (product.getPrice() != updatedProduct.getPrice()) {
    // product.setPrice(updatedProduct.getPrice());
    // }
    // if (!product.getDescription().equals(updatedProduct.getDescription())) {
    // product.setDescription(updatedProduct.getDescription());
    // }
    // this.ProductRepository.save(product);
    // return new RedirectView("/Admin/List-products");
    // }

    // @GetMapping("delete-product/{productId}")
    // public RedirectView deleteAccount(@PathVariable("productId") int ProductId) {
    // Product Product = this.ProductRepository.findByid(ProductId);
    // if (ProductId != 0) {
    // this.ProductRepository.delete(Product);
    // return new RedirectView("/Admin/List-products");
    // }
    // return new RedirectView("/Admin/List-products");
    // }

    @Autowired
    private productRepository productService;

    @GetMapping("/products")
    public RedirectView showAllproducts(Model model) {
        List<Product> products = productService.findAll(Sort.by(Sort.Direction.DESC, "id"));

        model.addAttribute("products", products);
        return new RedirectView("products/index");
    }

    @GetMapping("/create")
    public RedirectView createProduct(Model model) {
        productDto ProductDto = new productDto();
        model.addAttribute("ProductDto", ProductDto);
        return new RedirectView("products/createProduct");
    }

    @PostMapping("/create")
    public RedirectView saveProduct(@Valid @ModelAttribute productDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("products/createProduct");
        }

        // Check if image file is present
        MultipartFile image = productDto.getImagFile();
        if (image == null || image.isEmpty()) {
            result.addError(new FieldError("productDto", "imagFile", "Photo is required"));
            return new RedirectView("products/createProduct");
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
            return new RedirectView("products/createProduct");
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

        return new RedirectView("products");
    }

    @GetMapping("/edit")
    public RedirectView edit(@RequestParam int id, Model model) {
        Product product = productService.findById(id).get();
        model.addAttribute("product", product);
        productDto ProductDto = new productDto();
        ProductDto.setName(product.getName());
        ProductDto.setBrand(product.getBrand());
        ProductDto.setCategory(product.getCategory());
        ProductDto.setPrice(product.getPrice());
        ProductDto.setDescription(product.getDescription());
        model.addAttribute("ProductDto", ProductDto);
        return new RedirectView("products/editproduct");
    }

    @PostMapping("/edit")
    public RedirectView updateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute productDto ProductDto,
            BindingResult result) {
        try {
            Product product = productService.findById(id).get();
            model.addAttribute("product", product);

            if (result.hasErrors()) {
                return new RedirectView("products/EditProduct");
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
            product.setBrand(ProductDto.getBrand());
            product.setPrice(ProductDto.getPrice());

            // Save the updated product
            productService.save(product);

            return new RedirectView("products");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return new RedirectView("products");  
        }
    }

    @GetMapping("/delete")
    public RedirectView deleteproduct(@RequestParam int id) {
        try {
            Product prod = productService.findById(id).get();
            Path imagePath = Paths.get("static/" + prod.getImgFileName());
            try {
                Files.delete(imagePath);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
            productService.deleteById(id);
            return new RedirectView("products");// "redirect:/products";
        }

        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());

        }
        return new RedirectView("products");
        // return "redirect:/products";
    }

}
