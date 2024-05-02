package com.example.aswe.project.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aswe.project.models.Product;


public interface productRepository extends JpaRepository<Product,Integer> {
    Product findByProductId(int productId);
    // product findByProductid(int productId);
}
