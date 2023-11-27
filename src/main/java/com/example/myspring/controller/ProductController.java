package com.example.myspring.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.myspring.bean.MySqlConfigBean;
import com.example.myspring.model.BaseResponseModel;
import com.example.myspring.model.ProductModel;
import com.example.myspring.model.ProductResponseModel;
import com.fasterxml.jackson.databind.JsonSerializable.Base;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;

@Hidden
@CrossOrigin(origins = "*") // 允許不同網域的網頁來呼叫API
@RestController
public class ProductController extends BaseController {
    // 取得所有商品API
    @RequestMapping(value = "/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProduct() {
        ArrayList<ProductModel> result = getProductList();

        if(result == null) // 失敗
            return new ResponseEntity<Object>(new ProductResponseModel(1, "失敗", result), HttpStatus.OK);
        else // 成功
            return new ResponseEntity<Object>(new ProductResponseModel(0, "成功", result), HttpStatus.OK);
    }

    // 新增商品API
    @RequestMapping(value = "/product", method = RequestMethod.POST, 
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postProduct(@RequestBody ProductModel product) {
        String result = insertProduct(product);

        if(result.length() == 0)
            return new ResponseEntity<>(new BaseResponseModel(0, "成功"), HttpStatus.OK);
        else 
            return new ResponseEntity<>(new BaseResponseModel(1, result), HttpStatus.OK);
    }

    // 修改商品API
    @RequestMapping(value = "/product", method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity putProduct(@Parameter(description = "商品資訊") @RequestBody ProductModel product) {
        String result = updateProduct(product);

        if(result.length() == 0)
            return new ResponseEntity<>(new BaseResponseModel(0, "成功"), HttpStatus.OK);
        else 
            return new ResponseEntity<>(new BaseResponseModel(1, result), HttpStatus.OK);
    }
        
    // 刪除一筆商品API
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeProduct(@PathVariable int id) {
        String result = deleteProduct(id);

        if(result.length() == 0)
            return new ResponseEntity<>(new BaseResponseModel(0, "成功"), HttpStatus.OK);
        else 
            return new ResponseEntity<>(new BaseResponseModel(1, result), HttpStatus.OK);
    }

    protected ArrayList<ProductModel> getProductList() {
        try {
            connect();

            // 3. 取得Statement物件
            stmt = conn.createStatement();

            // 4. 下SQL拿資料
            rs = stmt.executeQuery("select * from product");

            // 5. 將SQL回傳的資料存到變數
            ArrayList<ProductModel> productEntities = new ArrayList<>();

            // 依序一筆一筆拿資料
            while(rs.next()) {
                ProductModel pe = new ProductModel();
                pe.setId(rs.getInt("id"));  // 取得id欄位資料並存到物件內
                pe.setPhotoUrl(rs.getString("photo_url"));
                pe.setTitle(rs.getString("title"));
                pe.setDescription(rs.getString("description"));
                pe.setPrice(rs.getInt("price"));
                pe.setStoreName(rs.getString("store_name"));
                pe.setStoreUrl(rs.getString("store_url"));

                productEntities.add(pe); // 存到陣列內
            }
            
            rs.close();
            stmt.close();
            conn.close();

            return productEntities;
        } catch(ClassNotFoundException e) {// 註冊驅動程式會出現的exception
            System.out.println("-------------------------------------");
            System.out.println(e.getMessage());
            return null;
        } catch(SQLException e) {
            System.out.println("-------------------------------------");
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected String insertProduct(ProductModel product) {
        try {
            connect();

            // 準備statement的SQL語法
            pstmt = conn.prepareStatement("INSERT INTO product VALUES(null, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, product.getPhotoUrl());
            pstmt.setString(2, product.getTitle());
            pstmt.setString(3, product.getDescription());
            pstmt.setInt(4, product.getPrice());
            pstmt.setString(5, product.getStoreUrl());
            pstmt.setString(6, product.getStoreName());

            pstmt.executeUpdate();

            return "";
        } catch(SQLException e) {
            return e.getMessage();
        } catch(ClassNotFoundException e) {
            return "ClassNotFoundException";
        }
    }

    // 資料庫操作: update
    protected String updateProduct(ProductModel product) {
        try {
            connect();

            pstmt = conn.prepareStatement("update product set photo_url=?, title=?, description=?, price=?, store_url=?, store_name=? where id=?");
            pstmt.setString(1, product.getPhotoUrl());
            pstmt.setString(2, product.getTitle());
            pstmt.setString(3, product.getDescription());
            pstmt.setInt(4, product.getPrice());
            pstmt.setString(5, product.getStoreUrl());
            pstmt.setString(6, product.getStoreName());
            pstmt.setInt(7, product.getId());
            pstmt.executeUpdate();

            return "";
        } catch(SQLException e) {
            return e.getMessage();
        } catch(ClassNotFoundException e) {
            return "ClassNotFoundException";
        }
    }

    // 資料庫操作: delete
    protected String deleteProduct(int id) {
        try {
            connect();

            pstmt = conn.prepareStatement("DELETE from product WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            return "";
        } catch(SQLException e) {
            return e.getMessage();
        } catch(ClassNotFoundException e) {
            return "ClassNotFoundException";
        }
    }
}
