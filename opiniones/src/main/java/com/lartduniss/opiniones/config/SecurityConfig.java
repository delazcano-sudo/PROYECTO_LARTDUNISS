package com.lartduniss.opiniones.config;


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
                // Consultar opiniones o promedios (GET): Accesible para CLIENTES y ADMINISTRADORES
                .requestMatchers(HttpMethod.GET, "/opiniones/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                
                // Publicar una reseña (POST): Permitido para CLIENTES y ADMINISTRADORES
                .requestMatchers(HttpMethod.POST, "/opiniones/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                
                // Moderación profunda (PUT / DELETE): Acción exclusiva para el ADMINISTRADOR
                .requestMatchers(HttpMethod.PUT, "/opiniones/**").hasAnyAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/opiniones/**").hasAnyAuthority("ADMINISTRADOR")
                
                // Cualquier otra ruta interna requiere estar autenticado
                .anyRequest().authenticated()
            )
            
            // 3. Manejo de excepciones en la cadena de filtros
            .exceptionHandling(exception -> exception
                // Cuando el usuario está autenticado pero no tiene rol (ej: CLIENTE tirando un DELETE)
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                
                // Cuando no se envían las cabeceras requeridas desde el Gateway
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(403);
                    response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Acceso denegado: No posees las credenciales o cabeceras de autenticación válidas para interactuar con las opiniones.\"}");
                })
            )
            
            // 4. Inyectamos tu filtro personalizado antes del filtro de autenticación por defecto
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}