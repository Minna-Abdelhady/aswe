package com.example.aswe.microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aswe.microservice.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByid(int id);
   
}
