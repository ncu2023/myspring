package com.example.myspring.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ShopCartResponseModel extends BaseResponseModel {
    ArrayList<ShopCartFullDTO> data;

    public ShopCartResponseModel(int code, String message, ArrayList<ShopCartFullDTO> data) {
        super(code, message);
        
        this.data = data;
    }
    
}
