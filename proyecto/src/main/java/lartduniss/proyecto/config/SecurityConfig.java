package lartduniss.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/clientes/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/error").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/clientes/**").permitAll() // Registro libre
                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/**").hasAnyRole("CLIENTE", "ADMINISTRADOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/clientes/**").hasAnyRole("CLIENTE", "ADMINISTRADOR", "ADMIN")
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(accessDeniedHandler())
            )
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}