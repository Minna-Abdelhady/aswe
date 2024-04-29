 package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.project.models.ShoppingCart;

public interface  CartRepository extends JpaRepository<ShoppingCart,Integer> {
    
}
