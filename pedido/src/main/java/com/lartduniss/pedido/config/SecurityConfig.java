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
            // 1. Desactivamos CSRF para permitir las operaciones POST/PUT externas
            .csrf(csrf -> csrf.disable())
            
            // 2. Definimos las reglas de autorización para la gestión de Pedidos
            .authorizeHttpRequests(auth -> auth
                
                // Consultar pedidos (GET): Permitido para CLIENTES y ADMINISTRADORES
                .requestMatchers(HttpMethod.GET, "/pedidos/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                
                // Generar un pedido (POST): Permitido para CLIENTES y ADMINISTRADORES
                .requestMatchers(HttpMethod.POST, "/pedidos/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                
                // Modificar el estado del pedido (PUT): Exclusivo del ADMINISTRADOR / Sistema interno
                .requestMatchers(HttpMethod.PUT, "/pedidos/**").hasAnyAuthority("ADMINISTRADOR")
                
                // Eliminar pedidos (DELETE): Exclusivo del ADMINISTRADOR
                .requestMatchers(HttpMethod.DELETE, "/pedidos/**").hasAnyAuthority("ADMINISTRADOR")
                
                // Cualquier otra ruta interna requiere autenticación previa
                .anyRequest().authenticated()
            )
            
            // 3. Manejo y encauzamiento de excepciones de seguridad
            .exceptionHandling(exception -> exception
                // Cuando el usuario está autenticado pero intenta vulnerar roles (ej: Cliente tirando un PUT)
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                
                // Cuando la cabecera X-User-* no viene en la petición o es inválida
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(403);
                    response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Acceso denegado: No se han proporcionado cabeceras de autenticación válidas para gestionar el pedido.\"}");
                })
            )
            
            // 4. Inyectamos tu filtro que lee los encabezados que vienen del Gateway
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}