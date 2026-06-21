package com.lartduniss.opiniones;

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
    "com.lartduniss.opiniones.controller",
    "com.lartduniss.opiniones.service",
    "com.lartduniss.opiniones.config",
    "exception"
})
@EntityScan(basePackages = {"com.lartduniss.opiniones.model"})
@EnableJpaRepositories(basePackages = {"com.lartduniss.opiniones.repository"})
class OpinionesApplicationTests {

    @Test
    void contextLoads() {
    
    }
}