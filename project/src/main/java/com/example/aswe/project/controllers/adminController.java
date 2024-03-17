package com.example.aswe.project.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.project.models.User;
import com.example.aswe.project.models.product;
import com.example.aswe.project.repositories.ProductRepository;
import com.example.aswe.project.repositories.adminRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/Admin")
public class adminController {
@Autowired
public adminRepository AdminRepository;

@Autowired
public ProductRepository productRepository;

@GetMapping("")
public ModelAndView getproducts() {
    ModelAndView mav = new ModelAndView("/html/admin/list-products.html");
    List<product> products = this.productRepository.findAll();
    mav.addObject("products", products);
    return mav;
}
@GetMapping("/admin/add-product")
public ModelAndView getAddproducts(){
    ModelAndView mav= new ModelAndView("/html/admin/AddProduct.html");
    return mav;
}
 @PostMapping("/admin/add-product")
public String addProduct(@ModelAttribute product Product){
    this.productRepository.save(Product);
  return "Added";
}

}
