package com.lartduniss.pagos;

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
@ComponentScan(basePackages = {
    "com.lartduniss.pagos",
    "com.lartduniss.pagos.config",
    "com.lartduniss.pagos.controller",
    "com.lartduniss.pagos.service",
    "com.lartduniss.pagos.exception"
})
@EntityScan(basePackages = {"com.lartduniss.pagos.model"})
@EnableJpaRepositories(basePackages = {"com.lartduniss.pagos.repository"})
class PagosApplicationTests {

    @Test
    void contextLoads() {
        // Con esto levanta el contexto perfectamente usando tus subpaquetes
    }
}