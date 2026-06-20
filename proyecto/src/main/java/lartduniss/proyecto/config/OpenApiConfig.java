package lartduniss.proyecto.config;

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
                        .title("API Lartduniss - Gestión de Clientes")
                        .version("1.0")
                        .description("Servicio encargado de la administración de usuarios y perfiles de compradores"))
                .servers(List.of(
                        new Server().url("http://localhost:8091").description("Puerto de Clientes en Universidad")
                ));
    }
}