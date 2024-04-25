package com.example.aswe.project.models;

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
    private shoppingCart ShoppingCart;


   

    public CartProducts(int id, String name, int price, int quantity,shoppingCart Shoppingcart) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.ShoppingCart =Shoppingcart  ;
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

    public shoppingCart getShoppingCart() {
        return this.ShoppingCart;
    }

    public void setShoppingCart(shoppingCart ShoppingCart) {
        this.ShoppingCart = ShoppingCart;
    }
    
}
