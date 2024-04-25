package com.example.aswe.project.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.models.CartProducts;
import com.example.aswe.project.models.shoppingCart;
import com.example.aswe.project.repositories.CartItemsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CartController {
    @Autowired
    private CartItemsRepository cartItems;
    @Autowired
    private shoppingCart Shoppingcart;
    @PostMapping("/add")
   public RedirectView addToCart(@RequestParam("id") int Id,@RequestParam("name") String Name,@RequestParam("price") int Price,@RequestParam("quantity" )int Quantity){
    CartProducts cartproducts= new CartProducts(Id,Name,Price,Quantity,Shoppingcart);
     cartItems.save(cartproducts);
     return new RedirectView("redirect:/list-cart");
   }
}
 