package com.example.myspring.model;

import lombok.Data;

// 所有API Response的父類別
@Data
public class BaseResponseModel {
    int code;
    String message;

    public BaseResponseModel(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
