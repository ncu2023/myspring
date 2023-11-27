package com.example.myspring.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ProductResponseModel extends BaseResponseModel {
    ArrayList<ProductModel> data;

    public ProductResponseModel(int code, String message, ArrayList<ProductModel> data) {
        super(code, message);

        this.data = data;
    }
}
