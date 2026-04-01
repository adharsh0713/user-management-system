# user-management-system

RESTful User Management System with authentication, role-based access control, and scalable architecture.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security + JJWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Validation | Spring Validation |
| Boilerplate | Lombok |

---

## Getting Started

### Prerequisites

- Java 21+
- PostgreSQL 13+
- Gradle 9.4.0+

### Installation

```bash
git clone https://github.com/adharsh0713/user-management-system.git
cd user-management-system
```

### Configuration

Create or update `src/main/resources/application.yaml`:

```yaml
spring:
  application:
    name: usermanagement
  datasource:
    url: jdbc:postgresql://localhost:5432/userdb
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  secret: ${JWT_SECRET}
  expiration: 3600000
```

> Never commit real credentials or JWT secrets to version control. Use environment variables or a secrets manager in production.

### Running the Application

```bash
./gradlew bootRun
```

---

## License

This project is licensed under the [MIT License](LICENSE).
