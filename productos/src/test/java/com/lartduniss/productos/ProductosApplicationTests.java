package com.lartduniss.productos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest(properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "springdoc.api-docs.enabled=false",         
    "springdoc.swagger-ui.enabled=false",
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
})
@ComponentScan(basePackages = "com.lartduniss.productos")
@EntityScan(basePackages = {"model"})
@EnableJpaRepositories(basePackages = {"repository"})
class ProductosApplicationTests {

    @Test
    void contextLoads() {
    }
}