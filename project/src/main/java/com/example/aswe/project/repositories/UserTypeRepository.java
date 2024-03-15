package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.project.models.UserType;

public interface UserTypeRepository  extends JpaRepository<UserType, Integer> {
    UserType findByname(String name);
}
