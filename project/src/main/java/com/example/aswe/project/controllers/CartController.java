package com.example.aswe.project.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.models.CartProducts;
import com.example.aswe.project.models.ShoppingCart;
import com.example.aswe.project.repositories.CartItemsRepository;
import com.example.aswe.project.repositories.cartRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/Cart")
public class CartController {
    @Autowired
    private CartItemsRepository cartItems;
    @Autowired
    private cartRepository Shoppingcart;
   
  @GetMapping("")
  public ModelAndView getshoppingcart() {
      ModelAndView mav= new ModelAndView("/html/user/list-cart.html");
      List<CartProducts> cartItems=this.cartItems.findAll();
      return mav;
  }
  

}
 