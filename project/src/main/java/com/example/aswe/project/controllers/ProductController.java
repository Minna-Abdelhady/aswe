// package com.example.aswe.project.controllers;

// import java.io.IOException;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.ModelAndView;

// import com.example.aswe.project.models.product;
// import com.example.aswe.project.repositories.ProductRepository;

// @RestController
// @RequestMapping("/product")
// public class ProductController {

//     @Autowired
//     private ProductRepository productRepository;
//     @GetMapping("/insertproduct")
//     public ModelAndView insertProduct() {
//         ModelAndView mav = new ModelAndView("insertProduct.html");
//         product newProduct = new product();
//         mav.addObject("product", newProduct);
//         return mav;
//     }
// @PostMapping("/product")
// public String saveProduct(@ModelAttribute product newProduct, @RequestParam("productImg") MultipartFile productImg) throws IOException {
//     if (!productImg.isEmpty()) {
//         newProduct.setProductImg(productImg.getBytes());
//     }
//     productRepository.save(newProduct);
//     return "Product saved successfully";
// }

// @GetMapping("store")
// public ModelAndView getProducts() {
//     ModelAndView mav = new ModelAndView("store.html"); // Assuming "store.html" is your store HTML page
//     List<product> products = this.productRepository.findAll();
//     mav.addObject("products", products);
//     return mav;
// }

    
// }
