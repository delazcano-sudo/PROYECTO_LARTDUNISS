package com.lartduniss.opiniones.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequestHeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String username = request.getHeader("X-User-Username");
        String rolesStr = request.getHeader("X-User-Roles");

        if (username != null && rolesStr != null && !rolesStr.trim().isEmpty()) {
            // Procesamos las autoridades limpias y en MAYÚSCULAS sin agregar "ROLE_"
            List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesStr.split(","))
                    .map(rol -> new SimpleGrantedAuthority(rol.trim().toUpperCase()))
                    .collect(Collectors.toList());

            // Creamos el token de autenticación de Spring Security
            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            
            // Seteamos la autenticación en el contexto seguro de Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continuar la cadena de filtros de forma segura
        filterChain.doFilter(request, response);
    }
}
