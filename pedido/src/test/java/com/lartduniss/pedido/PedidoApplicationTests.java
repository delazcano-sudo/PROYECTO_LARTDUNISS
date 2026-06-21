package com.lartduniss.pedido;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest(properties = {
   "spring.main.allow-bean-definition-overriding=true",
   "springdoc.api-docs.enabled=false",        
   "springdoc.swagger-ui.enabled=false",
   "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration",
   "spring.datasource.url=jdbc:h2:mem:testdb_pedido;DB_CLOSE_DELAY=-1;MODE=MySQL",
   "spring.datasource.driver-class-name=org.h2.Driver"
})
@ComponentScan(basePackages = {
   "com.lartduniss.pedido",
   "com.lartduniss.pedido.config",
   "com.lartduniss.pedido.controller",
   "com.lartduniss.pedido.service",
   "com.lartduniss.pedido.exception"
})
@EntityScan(basePackages = {"com.lartduniss.pedido.model"})
@EnableJpaRepositories(basePackages = {"com.lartduniss.pedido.repository"})
class PedidoApplicationTests {

   @Test
   void contextLoads() {
   }
}