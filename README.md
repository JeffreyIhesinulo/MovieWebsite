# MovieWebsite

A backend REST API for a movie catalog, built with Spring Boot and PostgreSQL. Models real-world relational data between movies, genres, actors, and directors, plus user registration with secure password storage.

## Tech Stack

- Java + Spring Boot
- PostgreSQL
- Hibernate / Spring Data JPA
- Lombok (`@Data`, `@Builder`)
- Spring Security (BCrypt password hashing)
- Maven

## Features (so far)

- **Movies** — CRUD operations, with `description`, `language`, `rating`, `duration`
- **Genres** — many-to-many relationship with movies
- **Actors** — many-to-many relationship with movies
- **Directors** — many-to-one relationship with movies
- **Users** — registration endpoint with BCrypt-hashed passwords (no plaintext storage)

Entity/DTO separation is used throughout to keep persistence models decoupled from API responses.

## Setup

1. Clone the repo
2. Make sure PostgreSQL is running locally
3. Update `src/main/resources/application.properties` with your local DB credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   ```
4. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```
   Tables are created/updated automatically on startup (`ddl-auto=update`).

## Status

Work in progress — learning project for practicing Spring Boot, JPA relationships, and relational database design. Next steps: login/JWT authentication, frontend integration.
