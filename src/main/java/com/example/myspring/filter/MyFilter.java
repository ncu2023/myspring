package com.example.myspring.filter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.myspring.model.BaseResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/*", filterName = "myFilter")
public class MyFilter extends OncePerRequestFilter {
    // 白名單
    final ArrayList<String> whiteListArrayList = new ArrayList<>(Arrays.asList(new String[]{"/login"}));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("My Filter getServletPath: " + request.getServletPath());
        System.out.println("My Filter getRequestURL: " + request.getRequestURL());

        if(whiteListArrayList.contains(request.getServletPath())) {
            // 不需要過濾
            filterChain.doFilter(request, response);
            return;
        }

        // 判斷有無登入
        HttpSession session = request.getSession();

        String username = (String)session.getAttribute("login");

        ObjectMapper objectMapper = new ObjectMapper();

        if(username == null) {
            // 未登入，直接response
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(objectMapper.writeValueAsString(new BaseResponseModel(999, "未登入(filter)")));
        } else {
            // 放行，到下一個filter或是api本體
            filterChain.doFilter(request, response);
        }
    }
    
}
