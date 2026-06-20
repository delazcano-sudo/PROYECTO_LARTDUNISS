package com.lartduniss.pagos.config;

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
                        .title("API Lartduniss - Sistema de Pago")
                        .version("1.0")
                        .description("Información de los pagos"))
                .servers(List.of(
                        new Server().url("http://localhost:8093").description("Puerto Local de Pagos") // Puerto 8093!!!!!!!!!!
                ));
    }
}