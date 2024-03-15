package com.example.aswe.project.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.aswe.project.models.User;
import com.example.aswe.project.models.product;

@Repository
public class ProductRepository extends JpaRepository<product, Long>  {

    public void save(product newProduct) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public List<product> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
