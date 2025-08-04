
# Sistema de Gestión de Productos e Inventario

Este repositorio contiene dos microservicios diseñados con **Spring Boot** que forman parte de un sistema distribuido para gestionar productos e inventario. Implementa principios de arquitectura limpia, desacoplamiento, y está preparado para integrarse en un ecosistema de microservicios.

---

## 🧱 Arquitectura del Sistema

El sistema sigue una arquitectura basada en microservicios con responsabilidades separadas:

```
+---------------------+       REST        +-------------------------+
| Productos Service   |  <------------->  | Inventario Service      |
+---------------------+                   +-------------------------+
| CRUD productos      |                   | Gestión de inventario   |
| Validaciones & DTOs |                   | Historial de compras    |
| Seguridad (API Key) |                   | Feign hacia productos   |
+---------------------+                   +-------------------------+
```

Cada servicio cuenta con controladores, servicios, excepciones, DTOs y configuración propia.

```graph TD
    subgraph Productos_Service["Productos Service"]
        A[Controlador] -->|GET /productos| B[Lógica de Negocio]
        A -->|GET /productos/{id}| B
        A -->|GET /swagger-ui| B
        A -->|GET /v3/api-docs| B
        B --> C[(Base de Datos)]
        B --> D[Consulta Producto]
        B --> E[API Key]
        B --> F[Inventario Service]
    end

    subgraph Inventario_Service["Inventario Service"]
        G[Controlador] -->|POST /agregar| H[Lógica de Negocio]
        G -->|PUT /comprar| H
        G -->|GET /{productoId}| H
        H --> I[(Base de Datos)]
        F --> G
    end
```

---

## 📂 Distribución de Carpetas

### Productos Service

```
src/main/java/com/linktic/productos/
├── config/               # Swagger, Interceptor, WebConfig
├── controller/           # Controladores REST
├── dto/                  # DTOs de entrada y salida
├── exception/            # Manejo de errores y excepciones
├── service/              # Lógica de negocio
└── ProductosServiceApplication.java
```

### Inventario Service

```
src/main/java/com/linktic/inventario/
├── client/               # Cliente Feign hacia Productos
├── config/               # Swagger, Interceptor, WebConfig
├── controller/           # Controladores REST
├── dto/                  # DTOs de entrada y salida
├── exception/            # Manejo de errores
├── mapper/               # Conversores entre entidades y DTOs
├── model/                # Entidades JPA
├── repository/           # Interfaces de acceso a datos
└── InventoryApplication.java
```

---

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Feign Client
- Swagger (OpenAPI)
- PostgreSQL
- Maven
- JUnit / Mockito

---

## ⚙️ Configuración del entorno

### PostgreSQL

Configura tu base de datos en `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventario_db
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🧪 Endpoints REST

### Productos Service (`http://localhost:8080`)

- `POST /productos` – Crear producto
- `GET /productos` – Listar productos
- `GET /productos/{id}` – Obtener producto
- `PUT /productos/{id}` – Actualizar producto
- `DELETE /productos/{id}` – Eliminar producto

### Inventario Service (`http://localhost:8081`)

- `POST /v1/inventario/agregar` – Agregar stock
- `POST /v1/inventario/compra` – Registrar compra
- `GET /v1/inventario/{productoId}` – Consultar inventario por producto

---

## 🔐 Seguridad

El servicio de productos requiere una clave API en las cabeceras HTTP:

```
X-API-KEY: tu_api_key_aqui
```

---

## 🔄 Comunicación entre servicios

El Inventario Service se comunica con el Productos Service usando un cliente Feign:

```java
@FeignClient(name = "producto-service", url = "${producto.service.url}")
public interface ProductoClient {
    @GetMapping("/api/producto/{id}")
    ProductoClientResponse obtenerProducto(@PathVariable Long id);
}
```

---

## 🧪 Pruebas

Ejecuta pruebas con:

```bash
mvn test
```

Pruebas de integración y unitarias se encuentran en las carpetas:

- `src/test/java/com/linktic/productos/integration/`
- `src/test/java/com/linktic/inventario/`

---

## 🐳 Contenedores y despliegue

Ambos servicios pueden contenerizarse con Docker.

### Dockerfile (ejemplo básico)

```dockerfile
FROM openjdk:17
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### docker-compose.yml (básico)

```yaml
version: "3.8"
services:
  productos:
    build: ./productos-service
    ports:
      - "8080:8080"

  inventario:
    build: ./inventario-service
    ports:
      - "8081:8081"
    environment:
      - producto.service.url=http://productos:8080
```

---

## 📘 Documentación Swagger

- Productos: `http://localhost:8080/api/swagger-ui/index.html`
- Inventario: `http://localhost:8081/api/swagger-ui/index.html`

---

## 🧑‍💻 Autor

Desarrollado por **Alexander Rubio Cáceres** para **Linktic**.

---

## 📝 Licencia

Este proyecto está bajo la licencia MIT. Puedes usarlo, modificarlo y distribuirlo libremente.
