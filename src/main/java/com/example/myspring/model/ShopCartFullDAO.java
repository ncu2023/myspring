package com.example.myspring.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shop_cart_holy")
public class ShopCartFullDAO {
    @Id
    int id;
        
    // int prdId;

    int quantity;

    LocalDateTime createdDate;

    int userId;

    @OneToOne
    @JoinColumn(name = "prdId")
    public ProductDAO productDAO;
}
