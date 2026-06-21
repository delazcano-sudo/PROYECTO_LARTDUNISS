package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI despachosOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Gestión de Despachos - Lartduniss")
                .version("1.0")
                .description("Documentación interactiva de los endpoints del microservicio de Despachos"))
            .servers(List.of(
                new Server().url("http://localhost:9090").description("Servidor a través del Gateway de la Pastelería")
            ));
    }
}