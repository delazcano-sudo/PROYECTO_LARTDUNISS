package com.lartduniss.pedido.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("L'art du niss - Microservicio de Pedidos")
                        .version("1.0")
                        .description("Documentación centralizada del Sistema de Pedidos y Ventas"))
                // --- Redirección obligatoria a través del API Gateway ---
                .servers(List.of(
                        new Server().url("http://localhost:8094").description("API Gateway")
                ));
    }
}
