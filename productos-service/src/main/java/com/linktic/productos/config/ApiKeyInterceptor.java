package com.linktic.productos.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class ApiKeyInterceptor implements HandlerInterceptor {

    private final String validApiKey;

    public ApiKeyInterceptor(String validApiKey) {
        this.validApiKey = validApiKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("X-API-Key");
        
        if (apiKey == null || !apiKey.equals(validApiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("{\"errors\":[{\"status\":\"401\",\"title\":\"Unauthorized\",\"detail\":\"API Key inválida o faltante\"}]}");
            return false;
        }
        
        return true;
    }
}