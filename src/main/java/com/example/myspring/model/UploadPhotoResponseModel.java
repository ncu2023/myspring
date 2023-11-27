package com.example.myspring.model;

import lombok.Data;

@Data
public class UploadPhotoResponseModel extends BaseResponseModel {
    String data; // 存放上傳後的路徑+檔名給前端

    public UploadPhotoResponseModel(int code, String message, String data) {
        super(code, message);

        this.data = data;
    } 
}