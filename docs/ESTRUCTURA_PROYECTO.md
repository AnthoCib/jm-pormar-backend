# Estructura del backend

```text
jm-pormar-backend/
├── docs/
│   ├── ESTRUCTURA_PROYECTO.md
│   └── MODELO_DATOS.md
├── requests/
│   └── jm-pormar-api.http
├── src/
│   └── main/
│       ├── java/com/jmpormar/
│       │   ├── JmPormarApplication.java
│       │   ├── auth/
│       │   │   ├── config/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── entity/
│       │   │   ├── repository/
│       │   │   ├── security/
│       │   │   └── service/
│       │   ├── dashboard/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   └── service/
│       │   ├── categoria/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── entity/
│       │   │   ├── mapper/
│       │   │   ├── repository/
│       │   │   └── service/
│       │   ├── producto/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── entity/
│       │   │   ├── mapper/
│       │   │   ├── repository/
│       │   │   └── service/
│       │   ├── servicio/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── entity/
│       │   │   ├── mapper/
│       │   │   ├── repository/
│       │   │   └── service/
│       │   ├── certificacion/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── entity/
│       │   │   ├── mapper/
│       │   │   ├── repository/
│       │   │   └── service/
│       │   ├── contacto/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── entity/
│       │   │   ├── repository/
│       │   │   └── service/
│       │   ├── archivo/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   └── service/
│       │   ├── config/
│       │   ├── exception/
│       │   └── shared/
│       │       ├── api/
│       │       ├── dto/
│       │       └── entity/
│       └── resources/
│           ├── db/migration/
│           │   ├── V1__crear_esquema_inicial.sql
│           │   └── V2__datos_iniciales.sql
│           ├── application.yml
│           ├── application-local.yml
│           └── application-docker.yml
├── .dockerignore
├── .env.example
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Responsabilidad de cada capa

### Controller

Recibe HTTP, valida DTO, delega al servicio y devuelve `ApiResponse`. No contiene reglas de negocio ni acceso directo a repositorios.

### DTO

Define contratos de entrada y salida. Las entidades JPA no se exponen directamente.

### Entity

Representa el modelo persistente y sus relaciones. Utiliza UUID y auditoría temporal.

### Mapper

Convierte entidades a DTO de respuesta y evita ciclos o exposición accidental de campos internos.

### Repository

Contiene operaciones JPA, búsquedas, filtros y conteos.

### Service

Implementa transacciones, reglas de negocio, validación de duplicados, activación/desactivación y coordinación entre módulos.

## Dependencias permitidas

```text
controller -> service -> repository/entity
                  |-> mapper
controller -> dto/shared
```

Un controller no debe utilizar un repository. Las reglas que involucren dos módulos deben residir en un service.
