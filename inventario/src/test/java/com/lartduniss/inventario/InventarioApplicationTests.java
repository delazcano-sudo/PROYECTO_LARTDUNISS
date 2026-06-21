package com.lartduniss.inventario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = InventarioApplicationTests.MockConfig.class)
class InventarioApplicationTests {

    @Configuration
    static class MockConfig {
    }

    @Test
    void contextLoads() {
    }
}