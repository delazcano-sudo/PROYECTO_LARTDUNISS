package com.lartduniss.pedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan(basePackages = {"com.lartduniss.pedido.model"})
@EnableJpaRepositories(basePackages = {"com.lartduniss.pedido.repository"})
public class PedidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidoApplication.class, args);
    }
//aca tambien añadimos el bean 
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}