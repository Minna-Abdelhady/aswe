package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.project.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByid(int id);
    // User findByEmail(String Email);
   
}
