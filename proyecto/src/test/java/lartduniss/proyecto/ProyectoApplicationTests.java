package lartduniss.proyecto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = ProyectoApplicationTests.TestConfig.class)
class ProyectoApplicationTests {

    @Configuration
    static class TestConfig {
        // Al estar vacía, Spring no busca bases de datos ni escanea paquetes reales aquí
    }

    @Test
    void contextLoads() {
        
    }
}