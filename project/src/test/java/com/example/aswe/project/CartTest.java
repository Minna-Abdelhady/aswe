package com.example.aswe.project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
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

    //set up preconditions before each test method
    @BeforeEach
    public void TestaddToCart(){
         shoppingcart= new ShoppingCart();
         cartproducts=new CartProducts();
         cartproducts.setId(1);
         cartproducts.setName("t11");
         cartproducts.setPrice(1100);
         cartproducts.setQuantity(5);
         shoppingcart.addProductToCart(cartproducts);

         //verify that the product was added successfully
         assertEquals(1,shoppingcart.getCartProductsList().size());
    }

}
