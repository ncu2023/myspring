package com.example.myspring.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.myspring.model.ShopCartDAO;
import com.example.myspring.model.ShopCartDTO;
import com.example.myspring.model.ShopCartFullDAO;
import com.example.myspring.model.ShopCartFullDTO;
import com.example.myspring.repository.ShopCartFullRepository;
import com.example.myspring.repository.ShopCartRepository;

@Service
public class ShopCartService {
    final private ShopCartRepository shopCartRepository;
    final private ShopCartFullRepository shopCartFullRepository;

    public ShopCartService(ShopCartRepository shopCartRepository, ShopCartFullRepository shopCartFullRepository) {
        this.shopCartRepository = shopCartRepository;
        this.shopCartFullRepository = shopCartFullRepository;
    }

    // 新增購物車資料
    // 更新購物車資料
    public boolean save(ShopCartDTO shopCartDTO) {
        System.out.println("ShopCartService");
        System.out.println(shopCartDTO);

        // 將DTO資料轉成DAO
        ShopCartDAO shopCartDAO = new ShopCartDAO();
        shopCartDAO.setId(shopCartDTO.getId());
        shopCartDAO.setPrdId(shopCartDTO.getPrdId());
        shopCartDAO.setQuantity(shopCartDTO.getQuantity());
        shopCartDAO.setUserId(shopCartDTO.getUserId());
        shopCartDAO.setCreatedDate(LocalDateTime.now());
        
        // 寫入資料庫
        ShopCartDAO result = shopCartRepository.save(shopCartDAO);
        
        return result != null;
    }

    // 查詢購物車全部資料
    public ArrayList<ShopCartFullDTO> findAll(int userId) {
        List<ShopCartFullDAO> result = shopCartFullRepository.findByUserId(userId);

        if(result != null) {
            ArrayList<ShopCartFullDTO> shopCartFullDTOs = new ArrayList<>();
            
            for(ShopCartFullDAO shopCartFullDAO : result) {
                // if(userId == shopCartFullDAO.getUserId()) {
                    System.out.println(shopCartFullDAO);
                    ShopCartFullDTO shopCartFullDTO = new ShopCartFullDTO();
                    shopCartFullDTO.setId(shopCartFullDAO.getId());
                    shopCartFullDTO.setQuantity(shopCartFullDAO.getQuantity());
                    shopCartFullDTO.setUserId(shopCartFullDAO.getUserId());
                    
                    // 如果join不到product，getProductDAO()方法會回傳null
                    if(shopCartFullDAO.getProductDAO() != null) {
                        shopCartFullDTO.setPrdId(shopCartFullDAO.getProductDAO().getId());
                        shopCartFullDTO.setPhotoUrl(shopCartFullDAO.getProductDAO().getPhotoUrl());
                        shopCartFullDTO.setTitle(shopCartFullDAO.getProductDAO().getTitle());
                        shopCartFullDTO.setDescription(shopCartFullDAO.getProductDAO().getDescription());
                        shopCartFullDTO.setPrice(shopCartFullDAO.getProductDAO().getPrice());
                    }
                
                    shopCartFullDTOs.add(shopCartFullDTO);
                // }
            }

            return shopCartFullDTOs;
        }

        return null;
    }

    // 刪除購物車資料
    public void delete(int id) {
        shopCartRepository.deleteById(id);
    }
}
 