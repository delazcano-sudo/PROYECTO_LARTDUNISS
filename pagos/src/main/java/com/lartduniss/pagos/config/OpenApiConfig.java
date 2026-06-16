package com.lartduniss.pagos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server; // <-- Importante agregar este import
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List; 

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Lartduniss - Sistema de Pago")
                        .version("1.0")
                        .description("Informacion de los pagos"))
                .servers(List.of(
                        new Server().url("http://localhost:8094").description("API Gateway")
                ));
    }
}
