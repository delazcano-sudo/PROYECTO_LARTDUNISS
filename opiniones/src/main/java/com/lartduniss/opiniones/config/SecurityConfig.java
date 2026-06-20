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
                
                .requestMatchers(HttpMethod.GET, "/opiniones/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                
               
                .requestMatchers(HttpMethod.POST, "/opiniones/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                
               
                .requestMatchers(HttpMethod.PUT, "/opiniones/**").hasAnyAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/opiniones/**").hasAnyAuthority("ADMINISTRADOR")
                
               
                .anyRequest().authenticated()
            )
            
           
            .exceptionHandling(exception -> exception
                
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                
              
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(403);
                    response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Acceso denegado: No posees las credenciales o cabeceras de autenticación válidas para interactuar con las opiniones.\"}");
                })
            )
            
           
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}