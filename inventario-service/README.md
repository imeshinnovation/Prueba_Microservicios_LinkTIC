
# Inventario Microservicio

Este microservicio forma parte de un sistema distribuido y se encarga de gestionar el inventario de productos y el historial de compras. Está desarrollado con Spring Boot y sigue principios de arquitectura limpia.

## 🧱 Estructura del proyecto

```
src/main/java/com/linktic/inventario/
├── client/                      # Cliente Feign para microservicio productos
├── config/                      # Configuraciones (Swagger, Interceptor, Web)
├── controller/                  # Controladores REST
├── dto/                         # Clases DTO para request y response
├── exception/                   # Manejadores y excepciones personalizadas
├── mapper/                      # Mapeadores entre entidades y DTOs
├── model/                       # Entidades JPA
├── repository/                  # Repositorios Spring Data
└── InventoryApplication.java    # Clase principal
```

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Feign Client
- Swagger (OpenAPI)
- PostgreSQL (configurable)
- JUnit / Mockito para pruebas unitarias

## ⚙️ Configuración del entorno

Asegúrate de tener configurado PostgreSQL y el siguiente archivo `application.properties` o `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventario_db
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 📦 Cómo ejecutar el proyecto

### Desde Maven
```bash
./mvnw spring-boot:run
```

### Desde un IDE (IntelliJ / Eclipse)
Ejecuta la clase `InventoryApplication.java` como aplicación Spring Boot.

## 🧪 Pruebas

Ejecuta las pruebas con:
```bash
./mvnw test
```

## 📘 Endpoints expuestos

Una vez en ejecución, puedes ver la documentación Swagger en:
```
http://localhost:8081/api/swagger-ui/index.html
```

### Endpoints principales

- `POST /v1/inventario/agregar`: Agrega productos al inventario.
- `POST /v1/inventario/compra`: Realiza una compra (actualiza inventario y registra historial).
- `GET /v1/inventario/{productoId}`: Consulta inventario por ID de producto.

## 🔄 Interacción con otros microservicios

Este servicio se comunica con el microservicio de productos usando un cliente Feign:

```java
@FeignClient(name = "producto-service", url = "${producto.service.url}")
public interface ProductoClient {
    @GetMapping("/api/producto/{id}")
    ProductoClientResponse obtenerProducto(@PathVariable Long id);
}
```

## 📂 Historial de compras

Las compras se registran en la entidad `HistorialCompra` y se almacenan en la base de datos.

## ⚠️ Manejo de errores

Incluye un `GlobalExceptionHandler` para capturar y responder errores personalizados como:

- `InventarioNotFoundException`
- `ProductoServiceException`

## 📝 Licencia

Este proyecto está bajo licencia MIT. Puedes usarlo, modificarlo y distribuirlo libremente.

---

Desarrollado por Alexander Rubio Cáceres, para Linktic
