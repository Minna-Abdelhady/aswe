package com.example.aswe.demo.services;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.aswe.demo.models.Product;

@Service
public class ProductService {
    
    private RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8082/users";

    public ProductService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Product> findAllProducts() {
        return restTemplate.exchange(
            baseUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Product>>() {}
        ).getBody();
    }
}
