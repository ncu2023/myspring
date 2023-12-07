package com.example.myspring.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.myspring.model.BaseResponseModel;
import com.example.myspring.model.ProductModel;
import com.example.myspring.model.ProductPageModel;
import com.example.myspring.model.ProductResponseModel;
import com.example.myspring.model.ProductV2ResponseModel;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;

// @Hidden

@CrossOrigin(origins = "*") // 允許不同網域的網頁來呼叫API
@RestController
public class ProductController extends BaseController {
    // 取得所有商品API
    @RequestMapping(value = "/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProduct(HttpSession session) {
        System.out.println("Product Seesion ID: " + session.getId());

        // 判斷有無登入過
        String username = (String)session.getAttribute("login");

        if(username == null) {
            return new ResponseEntity<Object>(new ProductResponseModel(999, "未登入", null), HttpStatus.OK);
        }

        ArrayList<ProductModel> result = getProductList();

        if(result == null) // 失敗
            return new ResponseEntity<Object>(new ProductResponseModel(1, "失敗", result), HttpStatus.OK);
        else // 成功
            return new ResponseEntity<Object>(new ProductResponseModel(0, "成功", result), HttpStatus.OK);
    }

    // 取得所有商品API v2
    @RequestMapping(value = "/v2/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProduct(int limit, int offset, int sortMode) {  // 0=升冪, 1=降冪
        ArrayList<ProductModel> result = getProductList(limit, offset, sortMode);
        int total = getProductCount();

        ProductPageModel data = new ProductPageModel();
        data.setProducts(result);
        data.setTotal(total);

        if(result == null || total == -1) // 失敗
            return new ResponseEntity<Object>(new ProductV2ResponseModel(1, "失敗", data), HttpStatus.OK);
        else // 成功
            return new ResponseEntity<Object>(new ProductV2ResponseModel(0, "成功", data), HttpStatus.OK);
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

    // 給取得商品資訊二代API用的方法
    protected ArrayList<ProductModel> getProductList(int limit, int offset, int sortMode ) {
        try {
            connect();

            // 3. 取得Statement物件
            stmt = conn.createStatement();

            String sort = sortMode == 0 ? "ASC" : "DESC";

            // 4. 下SQL拿分頁資料
            String queryString = "select * from product order by price " + sort + " limit " + limit + " offset " + offset;
            rs = stmt.executeQuery(queryString);

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

            pstmt.close();
            conn.close();

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

            pstmt.close();
            conn.close();

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

            pstmt.close();
            conn.close();

            return "";
        } catch(SQLException e) {
            return e.getMessage();
        } catch(ClassNotFoundException e) {
            return "ClassNotFoundException";
        }
    }

    // 取得商品總筆數
    protected int getProductCount() {
        try {
            connect();

            // 3. 取得Statement物件
            stmt = conn.createStatement();

            rs = stmt.executeQuery("select count(*) as c from product");

            // 取得總筆數
            rs.next();
            int total = rs.getInt("c");
            
            rs.close();
            stmt.close();
            conn.close();

            return total;
        } catch(ClassNotFoundException e) {// 註冊驅動程式會出現的exception
            System.out.println("-------------------------------------");
            System.out.println(e.getMessage());
            return -1;
        } catch(SQLException e) {
            System.out.println("-------------------------------------");
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
