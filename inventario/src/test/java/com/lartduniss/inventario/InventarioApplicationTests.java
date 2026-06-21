package com.lartduniss.inventario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "springdoc.api-docs.enabled=false",         
    "springdoc.swagger-ui.enabled=false"        
})
class InventarioApplicationTests {

    @Test
    void contextLoads() {
        
    }
}