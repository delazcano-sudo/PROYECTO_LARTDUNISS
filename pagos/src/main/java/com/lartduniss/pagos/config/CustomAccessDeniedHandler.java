package com.lartduniss.pagos.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler 
{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        // 1. Configurar el tipo de respuesta a JSON y el estado 403
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // 2. Construir un cuerpo JSON personalizado y claro para el usuario
        String jsonResponse = String.format(
            "{\"timestamp\": \"%s\", \"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Lo sentimos, tu cuenta de PACIENTE no tiene permisos para realizar esta acción (crear, modificar o eliminar registros).\"}",
            LocalDateTime.now()
        );

        // 3. Escribir la respuesta en el cuerpo
        response.getWriter().write(jsonResponse);
    }
}
