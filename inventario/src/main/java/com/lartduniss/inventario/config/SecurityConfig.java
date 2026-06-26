package com.lartduniss.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                // Documentación abierta
                .requestMatchers("/api/v1/inventario/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Reglas operativas para Inventario
                .requestMatchers(HttpMethod.GET, "/api/v1/inventario/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.POST, "/api/v1/inventario/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.PUT, "/api/v1/inventario/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/inventario/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(accessDeniedHandler())
            )
            .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:9090"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}