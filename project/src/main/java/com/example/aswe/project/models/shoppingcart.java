package com.example.aswe.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
  
    @ManyToOne
    CartProducts cartProducts;

    public ShoppingCart() {
    }

    public ShoppingCart(int id, String name) {
        this.id = id;
        this.name = name;
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


    public CartProducts getCartProducts() {
        return this.cartProducts;
    }

    public void setCartProducts(CartProducts cartProducts ) {
        this.cartProducts=cartProducts;
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
    
}
