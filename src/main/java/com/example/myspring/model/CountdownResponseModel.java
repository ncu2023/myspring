package com.example.myspring.model;

import lombok.Data;

@Data
public class CountdownResponseModel extends BaseResponseModel {
    String data;
    
    public CountdownResponseModel(int code, String message, String data) {
        super(code, message);

        this.data = data;
    }
}
