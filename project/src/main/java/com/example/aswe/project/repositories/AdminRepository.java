package com.example.aswe.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aswe.project.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
}
