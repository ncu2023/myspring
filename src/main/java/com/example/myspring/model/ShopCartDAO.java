package com.example.myspring.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shop_cart")
public class ShopCartDAO {
    @Id
    int id;

    int prdId;

    int quantitly;

    LocalDateTime createdDate;

    int userId;
}
