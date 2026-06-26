package com.lartduniss.api_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "jwt.secret=claveSecretaSuperSeguraDeMuchosCaracteresParaElEcosistemaLartduNiss2026"
    }
)
class ApiGatewayApplicationTest {

    @Test
    void contextLoads() {
        
    }

}