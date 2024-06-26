package com.example.aswe.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;



import com.example.aswe.project.models.Product;
import com.example.aswe.project.repositories.productRepository;
import com.example.aswe.project.models.productDto;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.validation.Valid;

import org.springframework.ui.Model;

import java.util.*;
@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private productRepository productService;
    
    @GetMapping("")
    public String showAllproducts(Model model){
        List<Product> products= productService.findAll(Sort.by(Sort.Direction.DESC,"id"));
        
        model.addAttribute("products",products);
        return "products/index";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        productDto ProductDto = new productDto();
        model.addAttribute("ProductDto", ProductDto);
        return "products/createProduct";
    }
    @PostMapping("/create")
    public String saveProduct(@Valid @ModelAttribute productDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return "products/createProduct";
    }

    // Check if image file is present
    MultipartFile image = productDto.getImagFile();
    if (image == null || image.isEmpty()) {
        result.addError(new FieldError("productDto", "imagFile", "Photo is required"));
        return "products/createProduct";
    }

    // Generate storage file name
    Date createdAt = new Date();
    String storageFileName = createdAt.getTime() + "-" + StringUtils.cleanPath(image.getOriginalFilename());

    // Define upload directory
    String uploadDir = "C:\\Users\\Egy Sky\\Documents\\GitHub\\SWE-project\\aswe\\project\\src\\main\\resources\\static\\images";

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
        return "products/createProduct";
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

    return "redirect:/products";
}

@GetMapping("/edit")
public String edit(@RequestParam int id, Model model){
    Product product = productService.findById(id).get();
        model.addAttribute("product", product);
        productDto ProductDto = new productDto();
        ProductDto.setName(product.getName());
        ProductDto.setBrand(product.getBrand());
        ProductDto.setCategory(product.getCategory());
        ProductDto.setPrice(product.getPrice());
        ProductDto.setDescription(product.getDescription());
        model.addAttribute("ProductDto", ProductDto);
        return "products/editproduct";
    } 

@PostMapping("/edit")
public String updateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute productDto ProductDto,
        BindingResult result) {
    try {
        Product product = productService.findById(id).get();
        model.addAttribute("product", product);

        if (result.hasErrors()) {
            return "products/EditProduct";
        }
        if (!ProductDto.getImagFile().isEmpty()) {
            // Delete old image
            String uploadDir = "C:\\\\Users\\\\Egy Sky\\\\Documents\\\\GitHub\\\\SWE-project\\\\aswe\\\\project\\\\src\\\\main\\\\resources\\\\static\\\\images";
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
    Files.copy(inputStream, Paths.get("C:\\Users\\Egy Sky\\Documents\\GitHub\\SWE-project\\aswe\\project\\src\\main\\resources\\static\\images/" + storageFileName), StandardCopyOption.REPLACE_EXISTING);
    product.setImgFileName(storageFileName);
}

        // Update the product with new data from the ProductDto
        product.setName(ProductDto.getName());
        product.setDescription(ProductDto.getDescription());
        product.setBrand(ProductDto.getBrand());
        product.setPrice(ProductDto.getPrice());

        // Save the updated product
        productService.save(product);

        return "redirect:/products";
    } catch (Exception ex) {
        System.out.println("Exception: " + ex.getMessage());
        return "redirect:/products";
    }
}

@GetMapping("/delete")
public String deleteproduct(@RequestParam int id){
try{
Product prod=productService.findById(id).get();
 Path imagePath=Paths.get("C:\\Users\\Egy Sky\\Documents\\GitHub\\SWE-project\\aswe\\project\\src\\main\\resources\\static\\images" + prod.getImgFileName());
try{
    Files.delete(imagePath);
}
catch (Exception ex) {
    System.out.println("Exception: " + ex.getMessage());
}
productService.deleteById(id);
return "redirect:/products";
}

catch (Exception ex) {
    System.out.println("Exception: " + ex.getMessage());
   
}

return"redirect:/products";
}
}
    