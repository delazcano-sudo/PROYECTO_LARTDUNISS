package lartduniss.proyecto.reportes.config;

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
                        .title("API Lartduniss - Auditoría y Reportes")
                        .version("1.0")
                        .description("Servicio encargado del procesamiento de balances generales del ecosistema"))
                .servers(List.of(
                        new Server().url("http://localhost:8096").description("Puerto de Reportes")
                ));
    }
}