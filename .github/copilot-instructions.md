# Instructions for GitHub Copilot

- This project is a Java 17 application using Spring Boot 3.5.14
- The project uses Gradle as the build tool
- The project is structured in a typical Spring Boot manner, with packages for controllers, services, and repositories
- The project follows standard Java naming conventions and best practices
- The project uses Lombok for reducing boilerplate code
- The controller layer handles HTTP requests and responses, then delegates business logic to the service layer
- The controller layer stores controllers in the package controller and uses the @RestController annotation
- The controller layer stores DTOs (Data Transfer Objects) for request and response payloads in the package controller.dto
- The service layer contains the core business logic and interacts with the repository layer for data access
- The service layer stores services in the package service and uses the @Service annotation
- The service layer stores domain models in the package service.domain
- The repository layer uses Spring Data JPA for database interactions
- The repository layer stores repositories in the package repository and uses the @Repository annotation
- The repository layer stores JPA entities in the package repository.entity
- The project uses an H2 in-memory database for development and testing purposes
- Configurations beans are stored in the package configuration and use the @Configuration annotation
- Utility classes are stored in the package util
- The project includes unit tests for each layer, with test classes stored in the corresponding test packages (e.g., controller, service, repository)
- The project uses standard Spring Boot annotations such as @RestController, @Service, @Repository, @Entity, and @Configuration
- The project follows a layered architecture, ensuring separation of concerns and maintainability
- The project includes proper error handling and validation mechanisms
- The project uses standard logging practices with SLF4J and Logback

