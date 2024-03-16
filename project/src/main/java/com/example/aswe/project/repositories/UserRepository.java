package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.aswe.project.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByid(int id);

    // <List>User findByemail(String Email);
   
}
