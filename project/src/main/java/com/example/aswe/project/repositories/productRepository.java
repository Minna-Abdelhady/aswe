package com.example.aswe.project.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aswe.project.models.product;


public interface productRepository extends JpaRepository<product,Integer> {
    product findByProductId(int productId);
    // product findByProductid(int productId);
}
