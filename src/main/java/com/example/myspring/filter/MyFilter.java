package com.example.myspring.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*", filterName = "myFilter")
public class MyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("My Filter getServletPath: " + request.getServletPath());
        System.out.println("My Filter getRequestURL: " + request.getRequestURL());

        // 放行，到下一個filter或是api本體
        filterChain.doFilter(request, response);
    }
    
}
