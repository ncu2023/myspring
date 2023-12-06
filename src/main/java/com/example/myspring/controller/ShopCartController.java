package com.example.myspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myspring.model.BaseResponseModel;
import com.example.myspring.model.ShopCartDTO;
import com.example.myspring.service.ShopCartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // 允許不同網域的網頁來呼叫API
@RestController
public class ShopCartController {
    final private ShopCartService shopCartService;

    public ShopCartController(ShopCartService shopCartService) {
        this.shopCartService = shopCartService;
    }

    @PostMapping("/api/v1/shopcart")
    public ResponseEntity postShopCart(@RequestBody ShopCartDTO shopcart) {
        System.out.println("postShopCart");
        System.out.println(shopcart);
        boolean result = shopCartService.add(shopcart);

        return result ?
            new ResponseEntity<Object>(new BaseResponseModel(0, "成功"), HttpStatus.OK):
            new ResponseEntity<Object>(new BaseResponseModel(1, "失敗"), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/shopcart/{id}")
    public ResponseEntity deleteShopCartItem(@PathVariable int id) {
        shopCartService.delete(id);

        return new ResponseEntity<Object>(new BaseResponseModel(0, "成功"), HttpStatus.OK);
    }
    
}
