package com.example.myspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myspring.model.BaseResponseModel;
import com.example.myspring.model.ShopCartDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // 允許不同網域的網頁來呼叫API
@RestController
public class ShopCartControll {
    @PostMapping("/api/v1/shopcart")
    public ResponseEntity postShopCart(@RequestBody ShopCartDTO shopcart) {
        System.out.println(shopcart);

        return new ResponseEntity<Object>(new BaseResponseModel(0, "成功"), HttpStatus.OK);
    }
}
