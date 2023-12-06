package com.example.myspring.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class ProductDAO {
    @Id
    int id;

    String photoUrl;
    String title;
    String description;
    int price;
    String storeUrl;
    String storeName;
}
