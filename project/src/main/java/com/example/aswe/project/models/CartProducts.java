package com.example.aswe.project.models;

import com.example.aswe.project.repositories.CartRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CartProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private int quantity;
    @ManyToOne
    private ShoppingCart shoppingcart;

    @ManyToOne
    private User user;

    @ManyToOne
    product Product;

    
    public CartProducts(){

    }

    public CartProducts(int id, String name, int price, int quantity,ShoppingCart shoppingcart) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shoppingcart =shoppingcart  ;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ShoppingCart getShoppingCart() {
        return this.shoppingcart;
    }

    public void setShoppingCart(ShoppingCart shoppingcart) {
        this.shoppingcart = shoppingcart;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public product getProduct() {
        return this.Product;
    }

    public void setProduct(product Product) {
        this.Product = Product;
    }
}
