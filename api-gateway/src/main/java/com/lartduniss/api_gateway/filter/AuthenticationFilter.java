package com.lartduniss.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Value("${jwt.secret}")
    private String secreto;

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {
        
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            
            if (path.contains("api-docs") || 
                path.contains("/swagger-ui") || 
                path.contains("/webjars") ||
                path.startsWith("/api/v1/usuarios/registrar") || 
                path.startsWith("/api/v1/usuarios/login")) {
                return chain.filter(exchange);
            }

            
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Token de autenticación faltante o con formato inválido.", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();

                List<?> rolesRaw = claims.get("roles", List.class);
                List<String> roles = rolesRaw.stream()
                        .map(Object::toString)
                        .toList(); 
                        
                String method = exchange.getRequest().getMethod().name();
                
                
                if (path.startsWith("/opiniones") && 
                    (method.equals("PUT") || method.equals("DELETE")) && 
                    roles.contains("CLIENTE")) {
                    
                    return onError(exchange, 
                        "Lo sentimos, tu cuenta no tiene permisos para realizar acciones de moderación o alteración estructural en el sistema de opiniones. Esta función está reservada para ADMINISTRADORES.", 
                        HttpStatus.FORBIDDEN);
                }

                if (path.startsWith("/api/v1/inventario") && 
                    (method.equals("POST") || method.equals("DELETE")) && 
                    roles.contains("CLIENTE")) {
                    
                    return onError(exchange, 
                        "Lo sentimos, tu cuenta no tiene permisos para registrar o purgar elementos del inventario directamente. Esta función está reservada para ADMINISTRADORES.", 
                        HttpStatus.FORBIDDEN);
                }
                
                String rolesStr = String.join(",", roles);

              
                ServerHttpRequest requestMutada = exchange.getRequest().mutate()
                        .header("X-User-Username", username)
                        .header("X-Username", username)
                        .header("X-User-Roles", rolesStr)
                        .build();
                return chain.filter(exchange.mutate().request(requestMutada).build());

            } catch (Exception e) {
                return onError(exchange, "El token JWT ha expirado o su firma es inválida.", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    @SuppressWarnings("null")
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        String jsonResponse = String.format(
            "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"mensaje\": \"%s\"}",
            LocalDateTime.now(),
            httpStatus.value(),
            httpStatus.getReasonPhrase(),
            err
        );

        byte[] bytes = Objects.requireNonNull(jsonResponse.getBytes(StandardCharsets.UTF_8));
        DataBuffer buffer = Objects.requireNonNull(response.bufferFactory().wrap(bytes));
        Mono<DataBuffer> body = Objects.requireNonNull(Mono.just(buffer));
        return response.writeWith(body);
    }
}