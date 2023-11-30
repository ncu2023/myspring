package com.example.myspring.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.myspring.model.BaseResponseModel;
import com.example.myspring.model.CountdownModel;
import com.example.myspring.model.CountdownResponseModel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;
import java.sql.Date;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*")
@RestController
public class CountdownController extends BaseController {
    @GetMapping(value="/v1/countdown")
    public ResponseEntity getCountdown() {
        Timestamp t = queryDeadline();
        System.out.println("==> " + t.getTime());
    
        if(t != null) {
            // 轉換時間格式給前端用
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String result = s.format(t);
            System.out.println("==> " + result);

            return new ResponseEntity<Object>(new CountdownResponseModel(0, "讀取deadline成功", result), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(new CountdownResponseModel(1, "讀取deadline失敗", ""), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/v1/countdown", method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity putCountdown(@RequestBody CountdownModel value) {
        String result = putCountdown(value.getCountdown());

        if(result.length() == 0)
            return new ResponseEntity<Object>(new BaseResponseModel(0, "成功"), HttpStatus.OK);
        else
            return new ResponseEntity<Object>(new BaseResponseModel(1, result), HttpStatus.OK);
    }

    // 從資料庫取得deadline datetime
    protected Timestamp queryDeadline() {
        try {
            connect();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * from count_down");
            rs.next();

            // Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            Timestamp t = rs.getTimestamp("deadline");
            
            rs.close();
            stmt.close();
            conn.close();

            return t;
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

    protected String putCountdown(long t) {
        try {
            connect();

            pstmt = conn.prepareStatement("UPDATE count_down set deadline = ?");
            // Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            pstmt.setTimestamp(1, new Timestamp(t));
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
