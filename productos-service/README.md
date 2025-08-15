
# Productos Service

Este proyecto es un servicio REST desarrollado en **Spring Boot**, diseñado para gestionar productos a través de operaciones CRUD. Implementa buenas prácticas como separación de capas, manejo centralizado de excepciones, y documentación automática con Swagger.

---

## 📦 Características principales

- API RESTful para gestión de productos.
- Validación de solicitudes mediante DTOs.
- Documentación interactiva con Swagger.
- Interceptores para autenticación con API Key.
- Manejo centralizado de excepciones.
- Tests de integración con Spring.

---

## 🧭 Estructura del Proyecto

```
src/
└── main/
    └── java/com/linktic/productos/
        ├── config/               # Configuraciones como Swagger y seguridad
        ├── controller/           # Controladores REST
        ├── dto/                  # Objetos de transferencia de datos
        ├── exception/            # Manejadores y clases de excepciones
        ├── service/              # Lógica del negocio (no incluida en los archivos cargados)
        └── ProductosServiceApplication.java  # Clase principal de Spring Boot
```

---

## 🚀 Cómo ejecutar el proyecto

### Prerrequisitos

- Java 17+
- Maven 3.6+

### Ejecución

```bash
mvn clean install
mvn spring-boot:run
```

El servicio se iniciará en: `http://localhost:8080`

---

## 🔐 Seguridad

El servicio utiliza un interceptor (`ApiKeyInterceptor`) para proteger los endpoints mediante una clave API que debe estar presente en los headers de cada solicitud:

```
X-API-KEY: tu_api_key_aqui
```

---

## 🧪 Endpoints REST

### `POST /productos`
Crea un nuevo producto.

### `GET /productos/{id}`
Obtiene los detalles de un producto por ID.

### `GET /productos`
Lista todos los productos.

### `PUT /productos/{id}`
Actualiza los detalles de un producto existente.

### `DELETE /productos/{id}`
Elimina un producto por ID.

---

## 📘 Documentación Swagger

Una vez el servicio esté corriendo, accede a la documentación interactiva en:

```
http://localhost:8080/api/swagger-ui/index.html
```

---

## ❌ Manejo de Errores

Las excepciones personalizadas como `ProductoNotFoundException` son gestionadas por un controlador global de excepciones (`GlobalExceptionHandler`) que devuelve respuestas consistentes en formato JSON.

---

## 🧪 Pruebas

Este proyecto incluye pruebas de integración ubicadas en:

```
src/test/java/com/linktic/productos/integration/
```

---

## 👨‍💻 Autor

Desarrollado por Alexander Rubio Cáceres

---

## 📝 Licencia

Este proyecto está bajo la licencia MIT.
