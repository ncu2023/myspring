package com.example.myspring.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductModel {
    @Schema(description = "商品id", example = "1")
    int id;

    @Schema(description = "商品照片網址", example = "https://www.xxx.com")
    String photoUrl;

    @Schema(description = "商品名稱")
    String title;

    @Schema(description = "商品描述")
    String description;

    @Schema(description = "商品價格")
    int price;

    @Schema(description = "商店圖片網址")
    String storeUrl;

    @Schema(description = "店家名稱")
    String storeName;
}
