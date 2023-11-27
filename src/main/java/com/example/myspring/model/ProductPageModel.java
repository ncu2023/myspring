package com.example.myspring.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ProductPageModel {
    ArrayList<ProductModel> products;
    int total;
}
