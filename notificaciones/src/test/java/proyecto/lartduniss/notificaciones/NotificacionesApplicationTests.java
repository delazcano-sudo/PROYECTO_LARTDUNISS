package proyecto.lartduniss.notificaciones;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(properties = {
   "spring.main.allow-bean-definition-overriding=true",
   "springdoc.api-docs.enabled=false",       
   "springdoc.swagger-ui.enabled=false",       
   "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
}, classes = NotificacionesApplicationTests.MockConfig.class)
class NotificacionesApplicationTests {

    @Configuration
    static class MockConfig {
    }

    @Test
    void contextLoads() {
    }
}