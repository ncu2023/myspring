package com.example.myspring.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.myspring.model.BaseResponseModel;
import com.example.myspring.model.UploadPhotoResponseModel;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*") // 允許不同網域的網頁來呼叫API
@RestController
public class UploadController {
    @Value("${upload.server.path}")
    private String serverUploadPath;

    // 寫成下面的語法即可 @RequestMapping(value = "/file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/file")
    @Tag(name="上傳檔案用")
    public ResponseEntity uploadFiles(@RequestParam("files") MultipartFile[] files) {
        // 取得上傳的原始檔名
        String filename = files[0].getOriginalFilename();
        System.out.println("上傳: " + filename);

        // 設定儲存的路徑
        String finalPath = new File(this.serverUploadPath + filename).getAbsolutePath();

        try {
            // 儲存檔案
            files[0].transferTo(new File(finalPath));
        } catch (IllegalStateException e) {
            return new ResponseEntity<Object>(new UploadPhotoResponseModel(1, "上傳失敗", e.getMessage()), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<Object>(new UploadPhotoResponseModel(2, "上傳失敗", e.getMessage()), HttpStatus.OK);
        }

        return new ResponseEntity<Object>(new UploadPhotoResponseModel(0, "上傳成功", "/images/" + filename), HttpStatus.OK);
    }
}
