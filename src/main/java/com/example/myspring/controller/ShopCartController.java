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
import org.springframework.web.bind.annotation.PutMapping;




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

        // id=0 代表一定會新增資料
        shopcart.setId(0);

        boolean result = shopCartService.save(shopcart);

        return result ?
            new ResponseEntity<Object>(new BaseResponseModel(0, "成功"), HttpStatus.OK):
            new ResponseEntity<Object>(new BaseResponseModel(1, "失敗"), HttpStatus.OK);
    }

    @PutMapping("/api/v1/shopcart")
    public ResponseEntity putShopCart(@RequestBody ShopCartDTO shopCartDTO) {
        if(shopCartDTO.getId() <= 0 ) {
            return new ResponseEntity<Object>(new BaseResponseModel(1, "失敗"), HttpStatus.OK);
        } else {
            boolean result = shopCartService.save(shopCartDTO);

            return result ?
                new ResponseEntity<Object>(new BaseResponseModel(0, "成功"), HttpStatus.OK):
                new ResponseEntity<Object>(new BaseResponseModel(1, "失敗"), HttpStatus.OK);
        }
    }

    @DeleteMapping("/api/v1/shopcart/{id}")
    public ResponseEntity deleteShopCartItem(@PathVariable int id) {
        shopCartService.delete(id);

        return new ResponseEntity<Object>(new BaseResponseModel(0, "成功"), HttpStatus.OK);
    }
    
}
