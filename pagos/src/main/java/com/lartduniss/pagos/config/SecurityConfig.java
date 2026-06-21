package com.lartduniss.pagos.config;

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
                // Acceso público para Swagger de Pagos
                .requestMatchers("/api/v1/pagos/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Endpoints protegidos
                .requestMatchers(HttpMethod.GET, "/api/v1/pagos/**").hasAnyAuthority("ADMINISTRADOR", "CONTADOR")
                .requestMatchers(HttpMethod.POST, "/api/v1/pagos/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/pagos/**").hasAnyAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/pagos/**").hasAnyAuthority("ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(403);
                    response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Acceso denegado: No posees los privilegios requeridos para realizar acciones de pago.\"}");
                })
            )
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}