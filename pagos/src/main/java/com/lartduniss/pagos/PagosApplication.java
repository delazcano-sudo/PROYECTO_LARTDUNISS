package com.lartduniss.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.lartduniss.pagos",
    "com.lartduniss.pagos.config",
    "com.lartduniss.pagos.controller",
    "com.lartduniss.pagos.service",
    "com.lartduniss.pagos.exception"
})
@EntityScan(basePackages = {"com.lartduniss.pagos.model"})
@EnableJpaRepositories(basePackages = {"com.lartduniss.pagos.repository"})
public class PagosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagosApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}