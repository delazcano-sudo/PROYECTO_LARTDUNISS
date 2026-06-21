package com.lartduniss.productos.config;

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
                        .title("API Lartduniss - Catálogo de Productos")
                        .version("1.0")
                        .description("Servicio encargado de la gestión de existencias y precios de catálogo"))
                .servers(List.of(
                        new Server().url("http://localhost:9090").description("API Gateway (Entrada Unificada)")
                ));
    }
}