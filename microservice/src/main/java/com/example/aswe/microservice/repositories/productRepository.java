package com.example.aswe.microservice.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aswe.microservice.models.Product;


public interface productRepository extends JpaRepository<Product,Integer> {
    // Product findByProductId(int productId);
    Product findByid(int id);
}
