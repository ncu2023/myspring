package com.example.myspring.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shop_cart")
public class ShopCartDAO {
    @Id

    int prdId;

    int quantity;

    @CreatedDate
    LocalDateTime createdDate;

    int userId;
}
