package com.example.aswe.project.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.project.models.CartProducts;
import com.example.aswe.project.models.ShoppingCart;
import com.example.aswe.project.models.User;
import com.example.aswe.project.models.Product;
import com.example.aswe.project.repositories.CartItemsRepository;
import com.example.aswe.project.repositories.CartRepository;
import com.example.aswe.project.repositories.UserRepository;
import com.example.aswe.project.repositories.productRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

  // @GetMapping("/cart/{id}")
  // public ModelAndView getshoppingcart(@PathVariable("id") int id) {
  // ModelAndView mav= new ModelAndView("/html/user/list-cart.html");
  // CartProducts cartproducts=this.cartItems.findById(id);
  // if(cartproducts!=null){

  // }
  // mav.addObject("cart", cartItems);
  // return mav;
  // }
  // @GetMapping("add-product")
  // public ModelAndView addproduct(){
  // ModelAndView mav= new ModelAndView("/html/user/list-product.html");
  // List<ShoppingCart> allcarts=this.Shoppingcart.findAll();
  // mav.addObject("allcarts", allcarts);
  // product newproduct=new product();
  // mav.addObject("product", newproduct);
  // return mav;
  // }

  // @PostMapping("add-product")
  // public String saveproduct(@ModelAttribute product product ){
  // this.ProductRepository.save(product);
  // return "Added";
  // }
  @GetMapping("/add-to-cart/{userId}/{productId}")
  public ModelAndView addToCart(@PathVariable("userId") int userId, @PathVariable("productId") int productId) {
    ModelAndView mav = new ModelAndView("/html/user/list-cart.html");
    User user = this.userRepository.findByid(userId);
    if (user != null) {
      Product product = this.ProductRepository.findByProductId(productId);
      if (product != null) {
        ShoppingCart shoppingcart = user.getShoppingCart();
        if (shoppingcart == null) {
          shoppingcart = new ShoppingCart();
          user.setShoppingCart(shoppingcart);
          CartProducts cartProducts = new CartProducts();
          cartProducts.setUser(user);
          cartProducts.setProduct(product);
          shoppingcart.addProductToCart(cartProducts);
          this.userRepository.save(user);
          mav.addObject("Product Added");
          return mav;
        }

      } else {
        ModelAndView errorMav = new ModelAndView("error.html");
        errorMav.addObject("errorMessage", "Product not found");
        return errorMav;
      }
    }
    return mav;
  }

  @GetMapping("/delete-cartItem/{userId}/{CartProducts_Id}")
  public ModelAndView deleteItem(@PathVariable("userId") int userId,
      @PathVariable("CartProducts_Id") int CartProducts_Id) {
    ModelAndView mav = new ModelAndView("/html/user/list-cart.html");
    User user = this.userRepository.findByid(userId);
    if (user != null) {
      ShoppingCart shoppingcart = user.getShoppingCart();
      if (shoppingcart == null) {
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
      if (cartProductsToDelete != null) {
        shoppingcart.getCartProductsList().remove(CartProducts_Id);
        userRepository.save(user);
        mav.addObject("successMessage", "Product deleted from cart successfully");
      } else {
        mav.addObject("errorMessage", "CartProduct not found in the shopping cart");
      }
    }
    return mav;
  }
}