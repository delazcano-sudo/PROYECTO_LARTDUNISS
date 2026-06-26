package com.lartduniss.pedido.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                
                .requestMatchers("/api/v1/pedidos/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api/v1/pedidos/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.POST, "/api/v1/pedidos/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/pedidos/**").hasAnyAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/pedidos/**").hasAnyAuthority("ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(403);
                    response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Acceso denegado: No se han proporcionado cabeceras de autenticación válidas para gestionar el pedido.\"}");
                })
            )
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}