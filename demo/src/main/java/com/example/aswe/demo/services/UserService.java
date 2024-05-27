package com.example.aswe.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.aswe.demo.models.User;

@Service
public class UserService {
    private RestTemplate restTemplate;
    // URL of the user microservice
    private final String baseUrl = "http://localhost:8082/users";

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> findAll() {
        return restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        ).getBody();
    }

    public User findById(int id) {
        return restTemplate.getForObject(baseUrl + "/" + id, User.class);
    }

    public void save(User user) {
        restTemplate.postForObject(baseUrl, user, User.class);
    }

    public void update(int id, User user) {
        restTemplate.put(baseUrl + "/" + id, user);
    }

    public void delete(int id) {
        restTemplate.delete(baseUrl + "/" + id);
    }
}

