package com.example.myspring.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myspring.bean.MySqlConfigBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class BaseController {
    @Autowired
    protected MySqlConfigBean mySqlConfigBean;

    protected Connection conn = null;  // 連接資料庫用
    protected Statement stmt = null;   // 下SQL語法用 
    protected PreparedStatement pstmt = null;
    protected ResultSet rs = null;     // 取得SQL結果用
    
    protected void connect() throws SQLException, ClassNotFoundException {
        // 1. 註冊驅動程式
        Class.forName(mySqlConfigBean.getMysqlDriverName());

        // 2. 連接資料庫
        conn = DriverManager.getConnection(mySqlConfigBean.getMysqlUrl() + 
            "?user=" + mySqlConfigBean.getMysqlUsername() + "&password=" + mySqlConfigBean.getMysqlPassword());
    }
}
