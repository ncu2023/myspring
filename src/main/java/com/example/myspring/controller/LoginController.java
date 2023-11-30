package com.example.myspring.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.myspring.model.AccountModel;
import com.example.myspring.model.BaseResponseModel;
import com.example.myspring.model.LoginResponseModel;
import com.example.myspring.model.ProductModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*") // 允許不同網域的網頁來呼叫API
@RestController  
public class LoginController extends BaseController {
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name="登入用API")
    @Operation(summary = "登入系統", description = "登入這個超級系統")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "打API成功", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = BaseResponseModel.class)) }),
        @ApiResponse(responseCode = "400", description = "打API失敗", 
            content = @Content), 
    })
    public ResponseEntity Login(@Parameter(description = "帳號", example = "aaron") String username, 
        @Parameter(description = "密碼", example = "1234") String password) { 
        // 登入
        String result = login(username, password);

        LoginResponseModel response = null;

        if(result.length() == 0) {
            // 成功
            response = new LoginResponseModel(0, "登入成功", username);
        } else {
            // 失敗
            response = new LoginResponseModel(1, result, username);
        }

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    // 驗證帳號跟密碼是否存在資料庫
    protected String login(String username, String password)  {
        try {
            // 連接資料庫
            connect();

            // 3. 取得statement物件
            stmt = conn.createStatement();

            // 4. 查詢資料庫
            rs = stmt.executeQuery("select count(*) as c from account a where name='" + username + "' and code='" + password + "';");

            // 取得c欄位的資料
            rs.next(); // 將資料指向第一筆
            int count = rs.getInt("c");

            rs.close();
            stmt.close();
            conn.close();

            // 沒有查詢到資料=登入失敗
            // if(count == 0) {
            //     return false;
            // } 
            // else {
            //     return true;
            // }

            return count != 0 ? "" : "帳號錯誤";

        } catch (ClassNotFoundException e) {
            
            return "ClassNotFoundException";
        } catch(SQLException e) {
            return e.getMessage();
        } 
    }

    // 註冊API
    @RequestMapping(value = "/v1/account", method = RequestMethod.POST, 
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postAccount(@RequestBody AccountModel account) {
        String result = insertAccount(account);

        if(result.length() == 0)
            return new ResponseEntity<>(new BaseResponseModel(0, "成功"), HttpStatus.OK);
        else 
            return new ResponseEntity<>(new BaseResponseModel(1, result), HttpStatus.OK);
    }

    protected String insertAccount(AccountModel account) {
        try {
            connect();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) as c from account WHERE name='" + account.getName() +"'");
            rs.next();
            int count = rs.getInt("c");

            // 帳號已經存在
            if(count >= 1) return "";

            // 準備statement的SQL語法
            pstmt = conn.prepareStatement("INSERT INTO account VALUES(null, ?, ?, ?, ?)");
            pstmt.setString(1, account.getName());
            pstmt.setString(2, account.getCode());
            pstmt.setString(3, account.getEmail());
            pstmt.setString(4, account.getPhone());
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
}
