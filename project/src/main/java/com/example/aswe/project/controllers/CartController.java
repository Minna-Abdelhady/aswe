package com.example.aswe.project.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.project.models.CartProducts;
import com.example.aswe.project.models.ShoppingCart;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.Product;
import com.example.aswe.project.repositories.CartItemsRepository;
import com.example.aswe.project.repositories.CartRepository;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.productRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Cart")
public class CartController {
  @Autowired
  private CartItemsRepository cartItems;
  @Autowired
  private CartRepository Shoppingcart;
  @Autowired
  private productRepository ProductRepository;
  @Autowired
  private UserRepository userRepository;

  @GetMapping("")
  public ModelAndView getProducts(HttpSession session) {
    ModelAndView mav = new ModelAndView("/html/user/list-cart.html");
    List<CartProducts> CartProducts = this.cartItems.findAll();
    if (CartProducts.size() != 0){
      List<CartProducts> myCartProducts = null;
      Integer userId = (Integer) session.getAttribute("id");
      if (userId != null){
        for (int i =0; i < CartProducts.size(); i++){
          if(CartProducts.get(i).getUser().getId() == userId){
            myCartProducts.add(CartProducts.get(i));
          }
        }
        mav.addObject("cartproducts", myCartProducts);
        return mav;
      }
      else{
        mav = new ModelAndView("error.html");
      mav.addObject("errorMessage", "Session is empty");
      return mav;
      }
      
    }
    mav = new ModelAndView("error.html");
    mav.addObject("errorMessage", "This cart is empty");
    return mav;
  }

  // @GetMapping("")
  // public List<CartProducts> getProducts() {
  //   List<CartProducts> CartProducts = this.cartItems.findAll();
  //   return CartProducts;
  // }

  @GetMapping("/add-to-cart/{userId}/{productId}")
  public RedirectView addToCart(@PathVariable("userId") int userId, @PathVariable("productId") int productId) {
    ModelAndView mav = new ModelAndView("/html/user/list-products.html");
    User user = this.userRepository.findByid(userId);
    if (user != null) {
      Product product = this.ProductRepository.findByid(productId);
      if (product != null) {
        ShoppingCart shoppingcart = user.getShoppingCart();
        if (shoppingcart == null) {
          shoppingcart = new ShoppingCart();
          user.setShoppingCart(shoppingcart);
          CartProducts cartProducts = new CartProducts();
          // cartProducts.setUser(user);
          // cartProducts.setProduct(product);
          shoppingcart.addProductToCart(cartProducts);
          this.userRepository.save(user);
          mav.addObject("Product Added");
          return new RedirectView("/Cart");
        }
      }
    }
      return new RedirectView("/Cart");
  }
  // @PostMapping("/add-to-cart/{userId}/{productId}")
  //   this.userRepository.save()
  // }
  
  // @GetMapping("/edit-cartItem/{userId}/{CartProducts_Id}")
  // public ModelAndView EditcartItem(@PathVariable("userId") int userId,
  // @PathVariable("CartProducts_Id") int CartProducts_Id,
  // @RequestParam("action") String action)
  // {
  // ModelAndView mav= new ModelAndView("/html/user/list-cart.html");
  // User user = this.userRepository.findByid(userId);
  // if(user !=null){
  // ShoppingCart shoppingcart= user.getShoppingCart();
  // if(shoppingcart==null){
  // mav.addObject("error message", "shoppingcart is empty");
  // return mav;
  // }
  // boolean ProductUpdated=
  // shoppingcart.editProductInCart(CartProducts_Id,action);
  // if(ProductUpdated){
  // mav.addObject("SusccessMessage","ShoppingCart is updtaed Successfully");
  // }
  // else{
  // mav.addObject("errorMessage","Cart item not found ");
  // }
  // }
  // else{
  // mav.addObject("errorMessage","User not Found");
  // }
  // return mav;

  @GetMapping("/delete-cartItem/{userId}/{CartProducts_Id}")
  public ModelAndView deleteItem(@PathVariable("userId") int userId,
      @PathVariable("CartProducts_Id") int CartProducts_Id) {
    ModelAndView mav = new ModelAndView("/html/user/list-cart.html");
    User user = this.userRepository.findByid(userId);
    if (user != null) {
      ShoppingCart shoppingcart = user.getShoppingCart();
      if (shoppingcart == null || shoppingcart.getCartProductsList().isEmpty()) {
        mav.addObject("errorMessage", "shoppin cart is already empty");
        return mav;
      }

      CartProducts cartProductsToDelete = null;
      for (CartProducts cartproducts : shoppingcart.getCartProductsList()) {
        if (cartproducts.getId() == CartProducts_Id) {
          cartProductsToDelete = cartproducts;
          break;
        }
      }
      if (cartProductsToDelete == null) {
        mav.addObject("error message", "product with specified id is not in the cart");
        return mav;
      }
      // shoppingcart.removeProductFromCart(cartProductsToDelete);
      userRepository.save(user);
      mav.addObject("successMessage", "Product deleted from cart successfully");
      return mav;
    }
    mav.addObject("errorMessage", "user not found");
    return mav;
  }
}
