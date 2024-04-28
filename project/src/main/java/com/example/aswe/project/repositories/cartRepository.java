package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.project.models.shoppingCart;

public interface cartRepository extends JpaRepository<shoppingCart,Integer>{
    shoppingCart findbyshoppingcartID(int id);
}
