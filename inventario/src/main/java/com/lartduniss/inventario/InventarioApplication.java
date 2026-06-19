package com.lartduniss.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.lartduniss.inventario.controller",
    "com.lartduniss.inventario.service",
    "com.lartduniss.inventario.config" // AGREGADO: Para escaneo de CORS y Swagger
})
@EntityScan(basePackages = {"com.lartduniss.inventario.model"})
@EnableJpaRepositories(basePackages = {"com.lartduniss.inventario.repository"})
public class InventarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventarioApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}