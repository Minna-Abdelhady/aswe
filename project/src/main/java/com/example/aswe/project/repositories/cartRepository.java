package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.project.models.shoppingcart;

public interface cartRepository extends JpaRepository<shoppingcart,long> {
    
    
}
