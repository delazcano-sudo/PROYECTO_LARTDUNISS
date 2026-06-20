package lartduniss.proyecto.reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "lartduniss.proyecto.reportes",
    "controller",
    "service",
    "lartduniss.proyecto.reportes.config"
})
@EntityScan(basePackages = {"model"})
@EnableJpaRepositories(basePackages = {"repository"})
public class ReportesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportesApplication.class, args);
    }
}