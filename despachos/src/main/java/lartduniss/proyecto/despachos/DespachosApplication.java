package lartduniss.proyecto.despachos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
   "lartduniss.proyecto.despachos",
   "controller",
   "service",
   "config",
   "exception"
})
@EntityScan(basePackages = {"model"})
@EnableJpaRepositories(basePackages = {"repository"})
public class DespachosApplication {
   public static void main(String[] args) {
       SpringApplication.run(DespachosApplication.class, args);
   }
}