package com.example.aswe.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.aswe.project.models.Admin;
import com.example.aswe.project.models.User;

@Repository
public interface adminRepository extends JpaRepository<Admin, Integer> {
    
    // @Query("SELECT u FROM User u WHERE u.type.id = :userTypeId")
    // List<User> findByUserTypeId(@Param("userTypeId") int userTypeId);

}
