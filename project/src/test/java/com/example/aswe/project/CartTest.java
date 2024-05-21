package com.example.aswe.project;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.aswe.project.controllers.CartController;
import com.example.aswe.project.models.CartProducts;
import com.example.aswe.project.models.ShoppingCart;
import com.example.aswe.project.models.User;
import com.example.aswe.project.repositories.UserRepository;

public class CartTest {
    private ShoppingCart shoppingcart=new ShoppingCart();
    private CartProducts cartproducts= new CartProducts();
    private User user;
    private UserRepository userRepository=mock(UserRepository.class);
     
    @Autowired
    private CartController cartcontroller;

}
