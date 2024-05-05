package com.example.aswe.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    private String name;
  
    @OneToMany
    private List<CartProducts> cartProductsList;
    
    @OneToOne
    private User user;

    @ManyToOne
    Product product;

    public ShoppingCart() {
    }

    public ShoppingCart(int cartId, String name,List<CartProducts> cartProductsList) {
        this.cartId = cartId;
        this.name = name;
        this.cartProductsList=cartProductsList;
    }   
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user=user;
    }
    public Product getProduct(){
        return product;
    }
    public void setProduct(Product product){
        this.product=product;
    }

    public int getId() {
        return this.cartId;
    }

    public void setId(int id) {
        this.cartId = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<CartProducts> getCartProductsList() {
        return cartProductsList;
    }

    public void setCartProductsList(List<CartProducts> cartProductsList) {
        this.cartProductsList =cartProductsList;
    }

    // public CartProducts getCartProducts() {
    //     return this.cartProducts;
    // }

    // public void setCartProducts(CartProducts cartProducts ) {
    //     this.cartProductsList=cartProducts;
    // }
    public void addProductToCart(CartProducts cartProduct){
        if(cartProductsList==null){
            cartProductsList=new ArrayList<>();
        }
        
            // CartProducts cartproducts=new CartProducts();
            // cartproducts.setProduct(product);
            // cartproducts.setUser(user);
            cartProductsList.add(cartProduct);
        
    }
    }

    //public ShoppingCart id(int id) {
        //setId(id);
       // return this;
   // }

    // public ShoppingCart name(String name) {
    //     setName(name);
    //     return this;
    // }

    // @Override
    // public boolean equals(Object o) {
    //     if (o == this)
    //         return true;
    //     if (!(o instanceof ShoppingCart)) {
    //         return false;
    //     }
    //     ShoppingCart shoppingCart = (ShoppingCart) o;
    //     return id == shoppingCart.id && Objects.equals(name, shoppingCart.name);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(id, name);
    // }

    // @Override
    // public String toString() {
    //     return "{" +
    //         " id='" + getId() + "'" +
    //         ", name='" + getName() + "'" +
    //         "}";
    // }
    

