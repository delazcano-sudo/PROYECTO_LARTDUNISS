package com.lartduniss.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lartduniss.productos", "controller", "service"})
@EntityScan(basePackages = {"com.lartduniss.productos.model", "model"})
@EnableJpaRepositories(basePackages = {"com.lartduniss.productos.repository", "repository"})
public class ProductosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductosApplication.class, args);
    }
}