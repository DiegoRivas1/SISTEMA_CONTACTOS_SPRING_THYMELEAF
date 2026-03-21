# 📋 Sistema de Contactos

![Java](https://img.shields.io/badge/Java-25-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?style=flat-square&logo=springboot)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-4.0.3-005F0F?style=flat-square&logo=thymeleaf)
![MySQL](https://img.shields.io/badge/MySQL-8+-blue?style=flat-square&logo=mysql)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-7952B3?style=flat-square&logo=bootstrap)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=flat-square&logo=apachemaven)

Sistema web para gestionar contactos desarrollado con **Spring Boot**, **Spring MVC**, **Thymeleaf** y **MySQL**, siguiendo arquitectura MVC con capa de servicios y repositorios.
---
## Demo
![Demo](screenshots/sistema_contactos_gif.gif)

---

## ✨ Funcionalidades

- ✅ Listar, crear, editar y eliminar contactos
- 🌍 Asociar contactos a un país
- 📞 Generar automáticamente el código internacional del celular
- 📊 Estadísticas de contactos activos e inactivos
- 🎨 UI responsiva con Bootstrap 5 y estilos personalizados

---

## 🛠️ Tecnologías

| Tecnología       | Versión  |
|-----------------|---------|
| Java            | 25      |
| Spring Boot     | 4.0.3   |
| Spring MVC      | 4.0.3   |
| Spring Data JPA | 4.0.3   |
| Thymeleaf       | 4.0.3   |
| MySQL           | 8+      |
| Maven           | 3.9+    |
| Lombok          | 1.18+   |
| Bootstrap       | 5.3     |
| spring-dotenv   | 4.0.0   |

---

## 🏗️ Arquitectura

El sistema sigue el patrón **MVC con Service Layer**:

```
Controller  →  Service  →  Repository  →  JPA / Hibernate  →  MySQL
```

### Flujo de una petición

```
[Navegador]
    │
    ▼
[ContactoControlador]   ← Spring MVC maneja la ruta
    │
    ▼
[ContactoServicio]      ← Lógica de negocio
    │
    ▼
[ContactoRepositorio]   ← Spring Data JPA
    │
    ▼
[MySQL - contactos_db]
```

---

## 📁 Estructura del proyecto

```
src
 ├── main
 │   ├── java/gm/contactos
 │   │   ├── controlador
 │   │   │   └── ContactoControlador.java
 │   │   │
 │   │   ├── modelo
 │   │   │   ├── Contacto.java
 │   │   │   └── Pais.java
 │   │   │
 │   │   ├── repositorio
 │   │   │   ├── ContactoRepositorio.java
 │   │   │   └── PaisRepositorio.java
 │   │   │
 │   │   └── servicio
 │   │       ├── IContactoServicio.java
 │   │       ├── ContactoServicio.java
 │   │       ├── IPaisServicio.java
 │   │       └── PaisServicio.java
 │   │
 │   └── resources
 │       ├── templates
 │       │   ├── index.html
 │       │   ├── agregar.html
 │       │   ├── editar.html
 │       │   └── fragmentos
 │       │       ├── cabecero.html
 │       │       ├── navegacion.html
 │       │       ├── tabla.html
 │       │       ├── tarjetas-cyberpunk.html
 │       │       └── pie-pagina.html
 │       │
 │       └── static
 │           ├── css
 │           │   ├── navbar.css
 │           │   ├── table.css
 │           │   └── agregar.css
 │           └── js
 │               └── navbar.js
```

---

## 🗄️ Modelo de datos

### Diagrama de relación

```
┌──────────────────────┐          ┌──────────────────────┐
│         Pais         │          │       Contacto       │
├──────────────────────┤          ├──────────────────────┤
│ PK  idPais           │◄────┐    │ PK  idContacto       │
│     nombrePais       │     │    │     nombre           │
│     codigoCelular    │     │    │     celular          │
└──────────────────────┘     │    │     email            │
                              │    │     estado           │
                              └────│ FK  pais_id          │
                                   └──────────────────────┘
         1                                    N
  (un país tiene muchos contactos)
```

### Entidad `Contacto`

```java
@Entity
public class Contacto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idContacto;
    String nombre;
    String celular;
    String email;
    boolean estado;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;

    // Métodos de presentación — mantienen Thymeleaf limpio
    public String getCelularCompleto() {
        return "+" + pais.getCodigoCelular() + " " + celular;
    }

    public String getEstadoTexto() {
        return estado ? "ACTIVO" : "INACTIVO";
    }
}
```

### Entidad `Pais`

```java
@Entity
public class Pais {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPais;
    private String nombrePais;
    private String codigoCelular;

    @OneToMany(mappedBy = "pais")
    private List<Contacto> contactos;
}
```

---

## 🔧 Capa de Repositorio

```java
// Extiende JpaRepository → findAll(), findById(), save(), delete() listos de fábrica
public interface ContactoRepositorio extends JpaRepository<Contacto, Integer> {

    // Spring genera el SQL automáticamente a partir del nombre del método
    List<Contacto> findByEstado(boolean estado);
}

public interface PaisRepositorio extends JpaRepository<Pais, Integer> { }
```

---

## ⚙️ Capa de Servicio

Cada entidad tiene su interfaz y su implementación:

```
IContactoServicio  ←  ContactoServicio  →  ContactoRepositorio
IPaisServicio      ←  PaisServicio      →  PaisRepositorio
```

Métodos disponibles en `ContactoServicio`:

| Método                         | Descripción                        |
|-------------------------------|-------------------------------------|
| `listarContactos()`            | Devuelve todos los contactos        |
| `buscarContactoPorId(id)`      | Busca por ID, retorna `null` si no existe |
| `guardarContacto(contacto)`    | Inserta si `id == null`, actualiza si no |
| `eliminarContacto(contacto)`   | Elimina el contacto                 |
| `buscarPorEstado(estado)`      | Filtra activos o inactivos          |

---

## 🗃️ Base de datos

La base de datos se crea automáticamente si no existe gracias a:

```
createDatabaseIfNotExist=true
```

Las tablas se generan a partir de las entidades JPA. No es necesario correr scripts SQL manualmente.

---

## 🔐 Variables de entorno

El proyecto usa un archivo `.env` para manejar credenciales. Se incluye `.env.example` como plantilla.

```env
APP_NAME=contactos

# ==========================
# BASE DE DATOS LOCAL
# ==========================

DB_USERNAME=your_user
DB_PASSWORD=your_password

DB_URL=jdbc:mysql://localhost:3306/contactos_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true


# ==========================
# BASE DE DATOS REMOTA
# ==========================

#DB_USERNAME=remote_user
#DB_PASSWORD=remote_password
#DB_URL=jdbc:mysql://remote_host:3306/contactos_db?sslMode=REQUIRED&serverTimezone=UTC


# ==========================
# SERVIDOR
# ==========================

SERVER_PATH=/
PORT=8080


# ==========================
# POOL DE CONEXIONES
# ==========================

CONNECTION_TIMEOUT=10000
MAXIMUM_POOL_SIZE=10
```

---

## 🚀 Instalación y ejecución

### ⚠️ Datos mínimos requeridos
La base de datos se genera automáticamente, pero la tabla pais debe tener al menos un registro antes de crear contactos. Sin países cargados, el formulario no mostrará opciones en el selector.
Puedes usar estos inserts de ejemplo:

```mysql
USE DATABASE contactos_db;

INSERT INTO pais(nombre_pais, codigo_celular) VALUES ('Peru','51');
INSERT INTO pais(nombre_pais, codigo_celular) VALUES ('Chile','56');
INSERT INTO pais(nombre_pais, codigo_celular) VALUES ('Mexico','52');
INSERT INTO pais(nombre_pais, codigo_celular) VALUES ('España','34');

```

### 1. Clonar el repositorio

```bash
git clone https://github.com/DiegoRivas1/SISTEMA_CONTACTOS_SPRING_THYMELEAF.git
cd SISTEMA_CONTACTOS_SPRING_THYMELEAF
```

### 2. Crear archivo de entorno

```bash
cp .env.example .env
# Editar .env con tus credenciales de MySQL
```

### 3. Ejecutar

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

### 4. Abrir en el navegador

```
http://localhost:8080
```

---

## 📦 Dependencias principales (`pom.xml`)

- `spring-boot-starter-web` — Spring MVC
- `spring-boot-starter-data-jpa` — Spring Data JPA / Hibernate
- `spring-boot-starter-thymeleaf` — Motor de plantillas
- `mysql-connector-j` — Driver MySQL
- `lombok` — Reduce boilerplate con anotaciones
- `spring-dotenv` — Soporte para archivo `.env`

---

## 🔭 Posibles mejoras futuras

- [ ] Autenticación con Spring Security
- [ ] API REST
- [ ] Paginación de contactos
- [ ] Búsqueda y filtrado de contactos
- [ ] Validación de formularios
- [ ] Dockerización

---

## 👤 Autor
**Diego Rivas**

Estudiante de Ciencias de la Computacion.

Proyecto desarrollado como práctica utilizando Spring Boot, arquitectura MVC, Thymeleaf y JPA/Hibernate.