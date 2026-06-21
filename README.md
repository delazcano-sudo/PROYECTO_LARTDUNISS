# L'art du niss - Sistema Ecosistema de Microservicios 

Este proyecto consiste en una arquitectura distribuida basada en microservicios desarrollada con **Spring Boot**, centralizada a través de un **API Gateway** y protegida mediante configuraciones estrictas de seguridad, CORS y validación de roles. El proyecto cuenta con documentación interactiva mediante OpenAPI/Swagger y una suite completa de pruebas unitarias aisladas.

## Herramientas de Instalación y Requisitos
Para compilar, probar y ejecutar localmente este ecosistema, necesitas contar con las siguientes herramientas:

1. **Java Development Kit (JDK):** Versión 21.
2. **Apache Maven:** Versión 3.9 o superior (para la gestión de dependencias y compilación).
3. **Gestor de Base de Datos:** MySQL Server (Puerto predeterminado `3306`).
4. **IDE Recomendado:** IntelliJ IDEA, Eclipse o Visual Studio Code con extensiones de Spring Boot.

### Bibliotecas y Dependencias Utilizadas
El proyecto utiliza el ecosistema de **Spring Boot 3.5.14** y las siguientes bibliotecas clave:

* **Spring Web:** Para la creación de las API RESTful.
* **Spring Cloud Gateway:** Como punto único de acceso y enrutamiento dinámico en el puerto `9090`.
* **Spring Data JPA & MySQL Driver:** Para la persistencia de datos y mapeo objeto-relacional.
* **Spring Security:** Para la interceptación de cabeceras de autenticación y protección selectiva de endpoints.
* **Spring Boot Validation:** Mapeo y validación de restricciones en las solicitudes (`@NotNull`, `@NotBlank`, etc.).
* **Spring HATEOAS:** Para añadir enlaces hipermedia autoexplicativos en las respuestas de los controladores.
* **Springdoc OpenAPI UI (v2.8.0):** Para la autogeneración de la documentación interactiva Swagger.
* **Lombok:** Para reducir el código boilerplate (Getters, Setters, Constructors).
* **JUnit 5 & Mockito (Scope Test):** Para pruebas unitarias, aserciones y simulación de la capa de persistencia.
* **H2 Database (Scope Test):** Base de datos en memoria para el aislamiento completo de las suites de prueba.

## Ejemplos Reales de Rutas para la API Rest (Via API Gateway - Puerto 9090)

Todas las solicitudes externas deben pasar estrictamente a través del API Gateway en el puerto `9090`. A continuación, se detallan ejemplos de rutas funcionales:

### Microservicio de Clientes (`/api/v1/clientes`)
* **GET** `http://localhost:9090/api/v1/clientes` - Listar todos los clientes (Requiere rol: `CLIENTE` o `ADMINISTRADOR`).
* **POST** `http://localhost:9090/api/v1/clientes` - Registrar un nuevo cliente.

### Microservicio de Pedidos (`/api/v1/pedidos`)
* **GET** `http://localhost:9090/api/v1/pedidos` - Listar pedidos con enlaces HATEOAS.
* **POST** `http://localhost:9090/api/v1/pedidos` - Crear un pedido (Inicializa automáticamente en estado `PENDIENTE_PAGO`).
* **PUT** `http://localhost:9090/api/v1/pedidos/{id}/estado` - Actualizar el estado del pedido (Acceso exclusivo: Rol `ADMINISTRADOR`).

### Microservicio de Pagos (`/api/v1/pagos`)
* **POST** `http://localhost:9090/api/v1/pagos` - Registrar una transacción o abono.

## Rutas de Acceso a Swagger (Documentación Centralizada)

Gracias a la configuración de agregación en el Gateway, puedes acceder a la documentación interactiva OpenAPI desde las siguientes rutas de entorno local:

* **Documentación en formato JSON del Microservicio de Pedidos:**
  `http://localhost:9090/api/v1/pedidos/v3/api-docs`

* **Documentación en formato JSON del Microservicio de Pagos:**
  `http://localhost:9090/api/v1/pagos/v3/api-docs`

* **Interfaz Gráfica de Swagger UI (Local de Pedidos):**
  `http://localhost:8094/swagger-ui.html`
