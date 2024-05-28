
package com.example.aswe.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String FName;


    @NotBlank(message = "Last name is required")
    @Size(min = 2, message = "Last name must have at least 2 characters")
    private String LName;

    @NotBlank(message = "Email is required")
    @jakarta.validation.constraints.Email(message = "Email should be valid")
    private String Email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String Password;
    // private List<CartItem> cart;

    @ManyToOne
    private UserType type = new UserType();

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

    // public void setType(UserType type) {
    //     this.type = type;
    // }

}
