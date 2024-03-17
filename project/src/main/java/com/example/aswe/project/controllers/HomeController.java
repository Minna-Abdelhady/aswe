package com.example.aswe.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.aswe.project.models.product;
import com.example.aswe.project.services.ProductService;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")    
    public String home(Model model) {
        List<product> products = productService.findAll();
        model.addAttribute("products", products);
        return "home";
    }
}