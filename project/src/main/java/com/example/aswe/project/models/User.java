package com.example.aswe.project.models;

import javax.validation.constraints.NotBlank;

import com.example.aswe.project.repositories.productRepository;

// import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters long")
    private String FName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters long")
    private String LName;

    @NotBlank(message = "Email is required")
    @javax.validation.constraints.Email(message = "Email must be a valid email address")
    private String Email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters long")
    private String Password;
    // private List<CartItem> cart;

    @ManyToOne
    private UserType type;
    
    // @OneToMany
    // private productRepository ProductRepository;

    @OneToOne
    private ShoppingCart shoppingCart;

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
     
    // public productRepository getProductRepository(){
    //     return this.ProductRepository;
    // }

    // public void setProductRepository(productRepository ProductRepository){
    //     this.ProductRepository=ProductRepository;
    // }

    public ShoppingCart getShoppingCart(){
        return this.shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart=shoppingCart;
    }

    // public List<CartItem> getCart() {
    //     return this.cart;
    // }

    // public void setCart(List<CartItem> cart) {
    //     this.cart = cart;
    // }
 

}

