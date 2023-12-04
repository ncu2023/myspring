package com.example.myspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title="健行Java養成班後端API", version = "1.0.0"))
@SpringBootApplication
@ServletComponentScan
public class MyspringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyspringApplication.class, args);
	}

}
