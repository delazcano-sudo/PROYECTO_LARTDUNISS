package com.lartduniss.productos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Forzamos la importación manual de tu filtro para que se comunique con tus paquetes externos
import com.lartduniss.productos.config.RequestHeaderAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll() 
                .requestMatchers(HttpMethod.POST, "/api/v1/productos/**").hasAnyAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/productos/**").hasAnyAuthority("ADMINISTRADOR") // Tu método PUT protegido
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(403);
                    response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Acceso restringido: No posees los privilegios de administrador para alterar el catálogo.\"}");
                })
            )
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}