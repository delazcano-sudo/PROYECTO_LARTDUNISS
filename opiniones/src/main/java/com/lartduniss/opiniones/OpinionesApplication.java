package com.lartduniss.opiniones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.lartduniss.opiniones.repository") // 
@EntityScan(basePackages = "com.lartduniss.opiniones.model")
@ComponentScan(basePackages = {
    "com.lartduniss.opiniones",
    "com.lartduniss.opiniones.controller",
    "com.lartduniss.opiniones.service",
    "com.lartduniss.opiniones.config",
    "exception"
})
public class OpinionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpinionesApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}