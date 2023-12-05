package com.example.myspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myspring.model.ShopCartDAO;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCartDAO, Integer>{
    
}
