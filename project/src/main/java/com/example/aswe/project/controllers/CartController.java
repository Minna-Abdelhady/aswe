package com.example.aswe.project.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aswe.project.models.CartItem;
import com.example.aswe.project.models.shoppingcart;
import com.example.aswe.project.repositories.cartRepository;

@RestController
@RequestMapping("/carts")
public class CartController {
  private final cartRepository CartRepository;
   public CartController(cartRepository CartRepository){
    this.CartRepository=CartRepository;
   }
   @GetMapping
   public List<shoppingcart>getAllCarts(){
    return CartRepository.findAll();
   }
   @SuppressWarnings("null")
  @PostMapping
   public shoppingcart createCart(@RequestBody shoppingcart cart){
    return CartRepository.save(cart);
   }
   @SuppressWarnings("null")
  @GetMapping("/{id}")
   public shoppingcart getCartById(@PathVariable Long id){
    return CartRepository.findById(id).orElseThrow(()-> new RuntimeException("Cart Not found"));
    
   }
   @SuppressWarnings("null")
  @PostMapping("/{id}/items")
   public shoppingcart addItemToCart(@PathVariable Long id,@RequestBody CartItem item){
    shoppingcart cart= CartRepository.findById(id).orElseThrow(()->new RuntimeException("cart not found"));
// cart.addItem(item);
    //  item.setCart cart=CartRepository.findById(id).orElseThrow(()->new RuntimeException("cart not found"));
  //  item.setCart(cart);
  //  cart.getItems().add(item);
   return CartRepository.save(cart);
}
}
