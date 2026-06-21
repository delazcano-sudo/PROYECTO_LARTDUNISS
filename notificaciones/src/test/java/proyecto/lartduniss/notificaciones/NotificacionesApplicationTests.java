package proyecto.lartduniss.notificaciones;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "springdoc.api-docs.enabled=false",        
    "springdoc.swagger-ui.enabled=false",        
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration" // Desactiva seguridad en el test
})
class NotificacionesApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto del microservicio levante perfectamente
    }
}