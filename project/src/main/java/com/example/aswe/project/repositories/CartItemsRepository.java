package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.project.models.CartProducts;

public interface CartItemsRepository extends JpaRepository<CartProducts,Integer>{
   // List<CartProducts> findAllByshoppingCartId(int id);
}
