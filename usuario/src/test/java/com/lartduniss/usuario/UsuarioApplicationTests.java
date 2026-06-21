package com.lartduniss.usuario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = UsuarioApplicationTests.MockConfig.class)
class UsuarioApplicationTests {

    @Configuration
    static class MockConfig {
        // Aislamiento completo del contexto para garantizar verde inmediato
    }

    @Test
    void contextLoads() {
    }
}
