# L'art du niss - Sistema Ecosistema de Microservicios 

Este proyecto consiste en una arquitectura distribuida basada en microservicios desarrollada con **Spring Boot**, centralizada a través de un **API Gateway** y protegida mediante configuraciones estrictas de seguridad, CORS y validación de roles. El proyecto cuenta con documentación interactiva mediante OpenAPI/Swagger y una suite completa de pruebas unitarias aisladas.

## Integrantes del Equipo:
* ** Demisse Lazcano **
* ** Victoria Rivera **

## Descripción del Contexto / Dominio del Proyecto
El sistema está diseñado para dar soporte integral al ecosistema de negocio de **L'art du niss** (pastelería/repostería fina: cupcakes, cakesicles, etc.). Permite la gestión centralizada de clientes, la administración automatizada de catálogos e inventarios, el procesamiento y orquestación de pedidos en tiempo real, la validación segura de pasarelas de pago, el control de despachos logísticos y la emisión de reportes administrativos mediante un control estricto de accesos y roles de usuario.

## Herramientas de Instalación y Requisitos
Para compilar, probar y ejecutar localmente este ecosistema, necesitas contar con las siguientes herramientas:

1. **Java Development Kit (JDK):** Versión 21.
2. **Apache Maven:** Versión 3.9 o superior.
3. **Gestor de Base de Datos:** MySQL Server (Puerto predeterminado `3306`).
4. **IDE Recomendado:** IntelliJ IDEA, Eclipse o Visual Studio Code.

### Bibliotecas y Dependencias Utilizadas
El proyecto utiliza el ecosistema de **Spring Boot 3.5.14** y las siguientes bibliotecas clave:
* **Spring Web**, **Spring Cloud Gateway** (Puerto `9090`), **Spring Data JPA & MySQL Driver**, **Spring Security**, **Spring Boot Validation**, **Spring HATEOAS**, **Springdoc OpenAPI UI (v2.8.0)**, **Lombok**, **JUnit 5 & Mockito**, y **H2 Database** (para pruebas).

## Listado de Microservicios Implementados

El ecosistema está compuesto por los siguientes servicios independientes desacoplados:
* **API Gateway:** Enrutador y punto único de entrada (Puerto `9090`).
* **MS-Clientes:** Gestión del perfil de clientes y validaciones (Puerto `8091`).
* **MS-Productos:** Catálogo de productos, cupcakes y repostería (Puerto `8092`).
* **MS-Pedidos:** Orquestador del flujo y estados de compra (Puerto `8093`).
* **MS-Pagos:** Procesamiento seguro de transacciones (Puerto `8094`).
* **MS-Usuarios:** Autenticación, roles y acceso administrativo (Puerto `8095`).
* **MS-Reportes:** Generación de métricas de negocio (Puerto `8096`).
* **MS-Despachos:** Logística y control de envíos de pedidos (Puerto `8097`).
* **MS-Opiniones:** Reseñas y feedback de productos (Puerto `8098`).
* **MS-Inventario:** Control de stock físico de insumos y productos (Puerto `8099`).
* **MS-Notificaciones:** Alertas automáticas del sistema (Puerto `8071`).

## Rutas Principales del Gateway (Puerto 9090)

Todas las solicitudes externas deben pasar estrictamente a través del API Gateway. A continuación se detallan ejemplos de las rutas base configuradas:

* **Clientes:** `GET` / `POST` ➔ `http://localhost:9090/api/v1/clientes`
* **Productos:** `GET` / `POST` ➔ `http://localhost:9090/api/v1/productos`
* **Pedidos:** `GET` / `POST` ➔ `http://localhost:9090/api/v1/pedidos`
* **Pagos:** `POST` ➔ `http://localhost:9090/api/v1/pagos`
* **Usuarios:** `POST` / `GET` ➔ `http://localhost:9090/api/v1/usuarios`
* **Inventario:** `GET` / `PUT` ➔ `http://localhost:9090/api/v1/inventario`

## Enlaces a la Documentación Swagger (Entorno Local)

La documentación interactiva OpenAPI/Swagger se encuentra centralizada y puede ser consumida desde el Gateway o directamente desde la interfaz de interfaz de usuario de cada módulo:

* **JSON de Documentación (Gateway):**
  * Pedidos: `http://localhost:9090/api/v1/pedidos/v3/api-docs`
  * Pagos: `http://localhost:9090/api/v1/pagos/v3/api-docs`
* **Interfaz Gráfica (Swagger UI Directo):**
  * Pedidos: `http://localhost:8094/swagger-ui.html`
  * Clientes: `http://localhost:8091/swagger-ui.html`
