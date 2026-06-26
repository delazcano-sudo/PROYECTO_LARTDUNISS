package lartduniss.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"lartduniss.proyecto", "controller", "service"})
@EntityScan(basePackages = {"model"})
@EnableJpaRepositories(basePackages = {"repository"})
public class ProyectoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }
}