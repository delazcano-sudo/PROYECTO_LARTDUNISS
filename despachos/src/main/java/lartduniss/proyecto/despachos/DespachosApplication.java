package lartduniss.proyecto.despachos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
"lartduniss.proyecto",
"controller",
"service",
"config", // AGREGADO
"lartduniss.proyecto.controller",
"lartduniss.proyecto.service",
"lartduniss.proyecto.config" // AGREGADO
})
@EntityScan(basePackages = {"model", "lartduniss.proyecto.model"})
@EnableJpaRepositories(basePackages = {"repository", "lartduniss.proyecto.repository"})
public class DespachosApplication {
public static void main(String[] args) {
SpringApplication.run(DespachosApplication.class, args);
}
}