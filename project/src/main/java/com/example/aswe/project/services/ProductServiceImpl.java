package com.example.aswe.project.services;

import com.example.aswe.project.models.Product;
import com.example.aswe.project.repositories.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private productRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}