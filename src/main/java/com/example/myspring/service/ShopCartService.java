package com.example.myspring.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.myspring.model.ShopCartDAO;
import com.example.myspring.model.ShopCartDTO;
import com.example.myspring.repository.ShopCartRepository;

@Service
public class ShopCartService {
    final private ShopCartRepository shopCartRepository;

    public ShopCartService(ShopCartRepository shopCartRepository) {
        this.shopCartRepository = shopCartRepository;
    }

    // 新增購物車資料
    public boolean add(ShopCartDTO shopCartDTO) {
        System.out.println("ShopCartService");
        System.out.println(shopCartDTO);

        // 將DTO資料轉成DAO
        ShopCartDAO shopCartDAO = new ShopCartDAO();
        shopCartDAO.setPrdId(shopCartDTO.getProId());
        shopCartDAO.setQuantity(shopCartDTO.getQuantity());
        shopCartDAO.setUserId(shopCartDTO.getUserId());
        shopCartDAO.setCreatedDate(LocalDateTime.now());
        
        // 寫入資料庫
        ShopCartDAO result = shopCartRepository.save(shopCartDAO);
        
        return result != null;
    }

    // 查詢購物車全部資料

    // 更新購物車資料

    // 刪除購物車資料
    public void delete(int id) {
        shopCartRepository.deleteById(27);
    }
}
