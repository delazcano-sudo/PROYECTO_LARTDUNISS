package lartduniss.proyecto.config;

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
                .requestMatchers(HttpMethod.POST, "/api/v1/clientes/**").permitAll() // Permitir registrarse libremente
                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/clientes/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(403);
                    response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"mensaje\": \"Acceso Denegado: Requiere iniciar sesión para gestionar este perfil de cliente.\"}");
                })
            )
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}