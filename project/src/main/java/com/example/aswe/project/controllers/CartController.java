package com.example.aswe.project.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.project.models.CartItem;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.product;
import com.example.aswe.project.models.shoppingcart;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.cartRepository;

@RestController
@RequestMapping("/carts")
public class CartController {
  private final cartRepository CartRepository;
  private UserRepository UserRepository;
  
   @Autowired
   public CartController(cartRepository CartRepository,UserRepository UserRepository){
    this.CartRepository=CartRepository;
    this.UserRepository=UserRepository;
   }
  
  
   @PostMapping
   public shoppingcart createCart(@RequestBody shoppingcart cart){
    return CartRepository.save(cart);
   }
   @GetMapping("/{id}")
   public shoppingcart getCartById(@PathVariable Long id){
    return CartRepository.findById(id).orElseThrow(()-> new RuntimeException("Cart Not found"));
    
   }

   @PostMapping("/add-to-cart/{userId}")
  public ModelAndView addItemToCart(@PathVariable("userId") int userId, @RequestBody CartItem item) {
    User user = this.UserRepository.findByid(userId);

    if (user != null) {
        // Add the item to the user's shopping cart
        user.getCart().add(item);

        // Save the updated user to the repository or database
        this.UserRepository.save(user);

        ModelAndView mav = new ModelAndView("shoppingcart.html");
        mav.addObject("CartItems", user.getCart());
        return mav;
    } else {
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }
}

@GetMapping("/update-cart/{userId}/increase")
public ModelAndView increaseCartItemsQuantity(@PathVariable("userId") int userId) {
    User user = this.UserRepository.findByid(userId);

    if (user != null) {
        List<CartItem> cartItems = user.getCart();

        for (CartItem cartItem : cartItems) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        this.UserRepository.save(user);
    }

    ModelAndView mav;
    if (user != null && !user.getCart().isEmpty()) {
        mav = new ModelAndView("shoppingcart.html");
        mav.addObject("CartItems", user.getCart());
    } else {
        mav = new ModelAndView("cart_empty.html");
        mav.addObject("message", "Your shopping cart is empty.");
    }

    return mav;
}

@GetMapping("/update-cart/{userId}/decrease")
public ModelAndView decreaseCartItemsQuantity(@PathVariable("id") int userId) {
    User user = this.UserRepository.findByid(userId);

    if (user != null) {
        List<CartItem> cartItems = user.getCart();

        for (Iterator<CartItem> iterator = cartItems.iterator(); iterator.hasNext();) {
            CartItem cartItem = iterator.next();
            int quantity = cartItem.getQuantity();
            if (quantity > 1) {
                cartItem.setQuantity(quantity - 1);
            } else {
                iterator.remove();
            }
        }

        this.UserRepository.save(user);
    }

    ModelAndView mav;
    if (user != null && !user.getCart().isEmpty()) {
        mav = new ModelAndView("shoppingcart.html");
        mav.addObject("CartItems", user.getCart());
    } else {
        mav = new ModelAndView("home.html");
        mav.addObject("message", "Your shopping cart is empty.");
    }

    return mav;
}
 
@GetMapping("/delete-cart/{userId}")
public ModelAndView deleteCart(@PathVariable("userId") int userId) {
   
    User user = this.UserRepository.findByid(userId);

    if (user != null) {
        // Clear the products in the user's shopping cart
        user.getCart().clear();

        // Save the updated user to the repository or database
        this.UserRepository.save(user);

        ModelAndView mav;
        if (user.getCart().isEmpty()) {
            mav = new ModelAndView("home.html");
            mav.addObject("message", "Your shopping cart is empty.");
        } else {
            mav = new ModelAndView("shoppingcart.html");
            mav.addObject("CartItems", user.getCart());
        }
        return mav;
    } else {
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "User not found");
        return errorMav;
    }
}
}
