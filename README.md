# Notification System

**Notification System** is a backend microservice built with Spring Boot that provides a simple REST API for managing
subscribers and sending notifications. It’s designed as a technical test with two types of users:

* **Subscriber**: can register and unregister to receive notifications.
* **Publisher**: can list registered subscribers and send notifications.

---

## Features

* **Subscriber endpoints** (public):

    * `POST /api/subscribers` — Subscribe a new user by email.
    * `DELETE /api/subscribers/{id}` — Unsubscribe an existing user.

* **Publisher endpoints** (protected - Basic Auth):

    * `GET /api/subscribers` — List all registered subscribers.
    * `POST /api/notifications` — Send a notification (title + message) to all active subscribers.

* **In-memory database** (H2) with console enabled for quick inspection.

* **Auto-generated API documentation** with SpringDoc (Swagger UI).

* **Basic HTTP security**: Subscriber endpoints are open; Publisher endpoints require `ROLE_PUBLISHER`.

---

## Tech Stack

* Java 21
* Spring Boot 3.2.x

    * Spring Web
    * Spring Data JPA (Hibernate)
    * Spring Security
    * SpringDoc OpenAPI UI
    * Spring Boot Starter Mail (optional)
* H2 in-memory database
* Maven build

---

## Getting Started

### Prerequisites

* JDK 21
* Maven 3.8+

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/tu-org/notification-system.git
   cd notification-system
   ```

2. **Build the project**

   ```bash
   mvn clean package -DskipTests
   ```

3. **Configure application** Review `src/main/resources/application.yml` to check or modify:

   ```yaml
   spring:
     datasource:
       url: jdbc:h2:mem:testdb
       driver-class-name: org.h2.Driver
       username: sa
       password:
     h2:
       console:
         enabled: true
         path: /h2-console
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true

     security:
       user:
         name: publisher
         password: "{noop}1234"
         roles: PUBLISHER

   springdoc:
     swagger-ui:
       path: /swagger-ui.html
   ```

### Run the application

```bash
mvn spring-boot:run
```

The service will start on `http://localhost:8080`.

---

## API Documentation

Once the application is running, you can explore the REST API via Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

This UI shows all endpoints, request/response schemas, and allows you to test them interactively.

---

## Authentication

* **Subscriber** endpoints (`POST /api/subscribers`, `DELETE /api/subscribers/{id}`) are **public**.
* **Publisher** endpoints (`GET /api/subscribers`, `POST /api/notifications`) require HTTP Basic Auth:

    * **Username**: `publisher`
    * **Password**: `1234`

Example with `curl`:

```bash
curl -u publisher:1234 http://localhost:8080/api/subscribers
```

---

## H2 Console

Access the H2 web console at:

```
http://localhost:8080/h2-console
```

* **JDBC URL**: `jdbc:h2:mem:testdb`
* **User**: `sa`
* **Password**: *empty*

---

## Testing

* **Unit tests**: `src/test/java` (run with `mvn test`).
* **Integration tests** with MockMvc to validate HTTP endpoints.

---
