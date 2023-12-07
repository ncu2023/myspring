
package com.example.myspring.model;

import lombok.Data;

/**
 * ShopCartFullDTO
 */
@Data
public class ShopCartFullDTO {
    int id;

    int prdId;

    int quantity;

    int userId;

    String photoUrl;

    String title;

    String description;

    int price;
}