package lartduniss.proyecto.reportes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = ReportesApplicationTests.MockConfig.class)
class ReportesApplicationTests {

    @Configuration
    static class MockConfig {
        
    }

    @Test
    void contextLoads() {
    }
}