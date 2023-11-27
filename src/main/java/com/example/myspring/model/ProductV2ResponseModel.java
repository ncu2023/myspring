package com.example.myspring.model;

import lombok.Data;

@Data
public class ProductV2ResponseModel extends BaseResponseModel {
    ProductPageModel data;

    public ProductV2ResponseModel(int code, String message, ProductPageModel data) {
        super(code, message);

        this.data = data;
    }
}
