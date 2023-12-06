package com.example.myspring.model;

import lombok.Data;

@Data
public class ShopCartDTO {
    int id;
    int proId;
    int quantity;
    int userId;
}
