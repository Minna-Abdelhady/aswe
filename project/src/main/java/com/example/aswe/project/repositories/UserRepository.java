package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.project.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
     User findByemail(String Email);
    // User findByUserID(int id);
    // User findByEmail(String Email);
    User findByid(int id);
    
}
