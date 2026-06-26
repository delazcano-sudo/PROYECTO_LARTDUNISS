package com.lartduniss.pedido.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
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
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        String username = request.getHeader("X-User-Username");
        if (username == null) {
            username = request.getHeader("X-Username");
        }
        String rolesStr = request.getHeader("X-User-Roles");

        if (username != null && rolesStr != null && !rolesStr.trim().isEmpty()) {
            List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesStr.split(","))
                    .flatMap(rol -> {
                        String normalized = rol.trim().toUpperCase();
                        return java.util.stream.Stream.of(
                                new SimpleGrantedAuthority(normalized),
                                new SimpleGrantedAuthority("ROLE_" + normalized)
                        );
                    })
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}