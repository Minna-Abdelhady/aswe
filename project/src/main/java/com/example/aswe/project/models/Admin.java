package com.example.aswe.project.models;

import jakarta.validation.constraints.Email;

import com.example.aswe.project.controllers.Valid;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "First name cannot be empty")
    private String FName;
    
    @NotEmpty(message = "Last name cannot be empty")
    private String LName;
   
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String Email;
   
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String Password;

    @Valid
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
