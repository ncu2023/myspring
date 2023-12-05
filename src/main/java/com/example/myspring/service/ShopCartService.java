package com.example.myspring.service;

import org.springframework.stereotype.Service;

import com.example.myspring.model.ShopCartDTO;

@Service
public class ShopCartService {
    public boolean add(ShopCartDTO shopCartDTO) {
        System.out.println("ShopCartService");
        System.out.println(shopCartDTO);

        // 將資料轉成DAO

        // 寫入資料庫

        return false;
    }
}
