# Gestión de cafés | Juan Pevé | Back-End / Java comisión #26223 | Talento Tech 2026

Aplicación web construida con **Spring Boot** en el backend y **HTML5 + Bootstrap 5 + JavaScript** en el frontend para gestionar el catálogo de cafés (alta, baja, modificación y control de stock).

## Descripción del Proyecto

- **CRUD de productos:** Crear, listar, editar y eliminar variedades de café (nombre, tamaño, precio y stock) desde una única vista con modal reutilizable.
- **Indicadores visuales de stock:** Etiquetas diferenciadas para stock disponible, bajo o agotado.
- **Diseño responsivo:** Maquetación con Bootstrap 5, Bootstrap Icons y estructura semántica HTML5.

## Estructura del Proyecto

```
    src/
    └── main/
        ├── java/
        │   └── com/
        │       └── techlab/
        │           └── coffee_api/
        │               ├── config/
        │               │   ├── CorsConfig.java
        │               │   └── DataInitializar.java
        │               ├── controller/
        │               │   └── CoffeeController.java
        │               ├── model/
        │               │   └── Coffee.java
        │               ├── repository/
        │               │   └── CoffeeRepository.java
        │               ├── service/
        │               │   ├── CoffeeService.java
        │               │   └── CoffeeServiceImpl.java
        │               └── CoffeeApiAplication.java
        └── resources/
            ├── application.properties
            └── static/
                ├── asstes/
                │   └── icon/
                │       └── cafeteria.png
                ├── css/
                │   └── styles.css
                ├── js/
                │   └── app.js
                └── index.html

```

## Tecnologías utilizadas

- Java (JDK 21 o superior recomendado)
- Spring Boot 4.1.2 con las dependencias Spring Web, Spring Data JPA y Validation
- Maven
- XAMPP
- Visual Studio Code con las extensiones de Java

El proyecto utiliza Maven como gestor de dependencias. Esto se identifica porque el proyecto contiene un archivo pom.xml en la raíz.

## Configuración de base de datos

El backend utiliza el motor de base de datos MySQL. La configuración se realiza en
application.properties, donde se define:

- URL de conexión a la base
- Usuario y contraseña de MySQL
- Driver JDBC
- Propiedades de JPA/Hibernate

# Requisitos previos

- Iniciar XAMP y activar los módulos Apache y MySQL
- Navegar a http://localhost/phpmyadmin/
- Crear la base de datos:

```
        CREATE DATABASE techlabdb;

```

## Carga inicial de datos

El proyecto incluye un CommandLineRunner que carga los productos solo si la base está vacía.
Esto permite iniciar el backend con datos listos para probar.

### Entidades

**Coffees**

- id: autogenerado
- nombre: obligatorio
- tamano: obligatorio
- precio: mayor que cero
- stock: no negativo

### Endpoints disponibles

**Coffees**

| Método | Endpoint        | Descripción               |
| ------ | --------------- | ------------------------- |
| GET    | `/coffees`      | Lista todos los productos |
| GET    | `/coffees/{id}` | Obtiene un producto       |
| POST   | `/coffees`      | Crea un producto          |
| PUT    | `/coffees/{id}` | Actualiza un producto     |
| DELETE | `/coffees/{id}` | Elimina un producto       |


## Cómo ejecutar el proyecto

El proyecto se ejecuta desde Visual Studio Code:

1. Abrir CoffeeApiApplication.java 
2. Presionar en Run
3. Abrir el navegador en http://localhost:8080

## 👨‍💻 Autor

Proyecto desarrollado por Juan para la entrega final de Back-End / Java.

## 📝 Licencia

Este proyecto fue realizado con fines educativos.