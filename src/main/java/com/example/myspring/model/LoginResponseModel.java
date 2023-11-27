package com.example.myspring.model;

import lombok.Data;

@Data
public class LoginResponseModel extends BaseResponseModel {
    String data; // 回傳帳號給前端

    public LoginResponseModel(int code, String message, String data) {
        super(code, message);

        this.data = data;
    }
}
