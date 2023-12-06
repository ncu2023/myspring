package com.example.myspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.myspring.model.ShopCartFullDAO;

@Repository
public interface ShopCartFullRepository extends JpaRepository<ShopCartFullDAO, Integer> {
    
}
