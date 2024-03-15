package com.example.aswe.project.models;

// import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String FName;
    private String LName;
    private String Email;
    private String Password;
    // private List<CartItem> cart;

    @ManyToOne
    private UserType type;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return this.FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return this.LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public UserType getType() {
        return this.type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    // public List<CartItem> getCart() {
    //     return this.cart;
    // }

    // public void setCart(List<CartItem> cart) {
    //     this.cart = cart;
    // }
 

}
