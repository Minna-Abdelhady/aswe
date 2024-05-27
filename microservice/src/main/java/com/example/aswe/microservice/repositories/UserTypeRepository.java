package com.example.aswe.microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.microservice.models.UserType;

public interface UserTypeRepository  extends JpaRepository<UserType, Integer> {
    UserType findByname(String name);
}
