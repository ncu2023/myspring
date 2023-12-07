package com.example.myspring.model;

import lombok.Data;

@Data
public class ShopCartDTO {
    int id;
    int prdId;
    int quantity;
    int userId;
}
