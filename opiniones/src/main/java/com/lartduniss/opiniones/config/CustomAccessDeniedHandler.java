package com.lartduniss.opiniones.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        String jsonResponse = String.format(
            "{\"timestamp\": \"%s\", \"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Lo sentimos, tu cuenta no tiene permisos para realizar acciones de moderación o alteración estructural en el sistema de opiniones. Esta función está reservada para ADMINISTRADORES.\"}",
            LocalDateTime.now()
        );

        response.getWriter().write(jsonResponse);
    }
}