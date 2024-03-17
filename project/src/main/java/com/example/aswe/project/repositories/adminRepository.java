package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.project.models.Admin;

public interface adminRepository extends JpaRepository<Admin,Integer> {
    
}
