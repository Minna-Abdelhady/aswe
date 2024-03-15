package com.example.aswe.project.models;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.aswe.project.repositories.TypeRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static final String TYPE_ADMIN = "Admin";
    public static final String TYPE_USER = "User";


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
    
}


@Component
public class TypeDataInitializer implements CommandLineRunner {

    private final TypeRepository typeRepository;

    public TypeDataInitializer(TypeRepository typeRepository){
        this.typeRepository = typeRepository;
    }

    @Override
    
}