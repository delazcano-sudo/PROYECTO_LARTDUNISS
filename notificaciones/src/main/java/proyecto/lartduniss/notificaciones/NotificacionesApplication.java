package proyecto.lartduniss.notificaciones;

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
"config", 
"lartduniss.proyecto.controller",
"lartduniss.proyecto.service"
})
@EntityScan(basePackages = {"model", "lartduniss.proyecto.model"})
@EnableJpaRepositories(basePackages = {"repository", "lartduniss.proyecto.repository"})
public class NotificacionesApplication {
public static void main(String[] args) {
SpringApplication.run(NotificacionesApplication.class, args);
}
}