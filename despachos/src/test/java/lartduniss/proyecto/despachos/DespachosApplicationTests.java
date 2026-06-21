package lartduniss.proyecto.despachos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = DespachosApplicationTests.MockConfig.class)
class DespachosApplicationTests {

    @Configuration
    static class MockConfig {
    }

    @Test
    void contextLoads() {
    }
}