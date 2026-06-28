# Demo Copilot with SQL H2

A modern Spring Boot 3.5.14 application demonstrating a clean layered architecture with a Product CRUD API. 
This project showcases **AI-assisted development using GitHub Copilot**, 
where the entire codebase was scaffolded and built using structured prompts to generate production-ready code.

## 🎯 Project Overview

This is a demonstration project that combines:
- **GitHub Copilot AI Assistance** for code generation and scaffolding
- **Spring Boot 3.5.14** for rapid application development
- **H2 In-Memory Database** for seamless development and testing
- **Layered Architecture** following Spring best practices
- **Comprehensive Testing** with unit tests for all layers

The project serves as a template for building scalable REST APIs with clean separation of concerns and 
maintainable code structure.

## ✨ Features

### Product CRUD API
- **Create Products** - POST endpoint to add new products with validation
- **Read Products** - GET endpoints to retrieve all products or a specific product by ID
- **Update Products** - PUT endpoint to modify existing products
- **Delete Products** - DELETE endpoint to remove products
- **Input Validation** - Bean Validation annotations ensure data integrity
- **Error Handling** - Global exception handler for consistent error responses
- **Logging** - SLF4J/Logback logging for debugging and monitoring

### Product Model
- `id` - Unique identifier (auto-generated)
- `name` - Product name (required)
- `description` - Product description
- `price` - Product price (required)
- `quantity` - Available quantity (required)

## 🏗️ Architecture

The application follows a **three-tier layered architecture** for clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                    Controller Layer                         │
│  - ProductController: REST endpoints (@RestController)      │
│  - ProductDTO: Data Transfer Objects for API requests/      │
│                responses                                    │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                    Service Layer                            │
│  - ProductService: Business logic (@Service)                │
│  - Product Domain: Business model objects                   │
│  - ModelMapper: Object transformation and mapping           │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                   Repository Layer                          │
│  - ProductRepository: Data access (@Repository)             │
│  - ProductEntity: JPA entities (@Entity)                    │
│  - Spring Data JPA: Database abstraction                    │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│              H2 In-Memory Database                          │
│  Auto-configured for development & testing                 │
└─────────────────────────────────────────────────────────────┘
```

### Directory Structure
```
src/main/java/com/example/demo/
├── controller/              # REST API endpoints
│   ├── ProductController.java
│   └── dto/
│       └── ProductDto.java
├── service/                 # Business logic
│   ├── ProductService.java
│   └── domain/
│       └── Product.java
├── repository/              # Data access layer
│   ├── ProductRepository.java
│   └── entity/
│       └── ProductEntity.java
├── configuration/           # Spring beans & configuration
│   ├── ModelMapperConfig.java
│   └── GlobalExceptionHandler.java
├── util/                    # Utility classes
└── DemoCopilotWithSqlH2Application.java

src/test/java/com/example/demo/
├── controller/
│   └── ProductControllerTest.java
├── service/
│   └── ProductServiceTest.java
└── repository/
```

## 🚀 Tech Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Java** | OpenJDK | 17 |
| **Framework** | Spring Boot | 3.5.14 |
| **Build Tool** | Gradle | 8.x |
| **Database** | H2 | In-Memory |
| **ORM** | Spring Data JPA | 3.5.14 |
| **Mapping** | ModelMapper | 3.1.1 |
| **Validation** | Bean Validation | Jakarta Validation API |
| **Logging** | SLF4J + Logback | Included in Spring Boot |
| **Boilerplate** | Lombok | Latest |
| **Testing** | JUnit 5 + Spring Test | Latest |

## 🤖 Development Approach: GitHub Copilot

This project was built entirely using **GitHub Copilot** with a structured prompt-based approach. 
The `.github/prompts/` directory contains the prompts that guided the AI-assisted development:

### Development Prompts

#### 1. `generate-standard-layers.prompt.md`
**Purpose**: Scaffolds the standard Spring Boot layered architecture

Generates the foundational package structure:
- Controller layer (`com.example.demo.controller`)
- Service layer (`com.example.demo.service`)
- Repository layer (`com.example.demo.repository`)
- Configuration layer (`com.example.demo.configuration`)
- Utility layer (`com.example.demo.util`)
- Corresponding test packages

#### 2. `add-modelmapper.prompt.md`
**Purpose**: Integrates ModelMapper for object transformation

Adds to the project:
- ModelMapper dependency in `build.gradle`
- `ModelMapperConfig` bean configuration
- Version compatibility with Spring Boot 3.5.14

#### 3. `add-crud-product.prompt.md`
**Purpose**: Generates complete CRUD implementation for Product entity

Implements:
- Product JPA entity with fields (id, name, description, price, quantity)
- Product service with business logic (create, read, update, delete)
- Product DTO for API contracts with validation annotations
- Product domain model for business operations
- ProductController with REST endpoints (POST, GET, PUT, DELETE)
- Unit tests for service and controller layers
- Postman collection for API testing
- Global exception handling and logging

### Benefits of This Approach

✅ **Consistency** - Prompts ensure uniform code structure and naming conventions  
✅ **Speed** - Rapid scaffolding and implementation of features  
✅ **Quality** - Generated code follows Spring Boot best practices  
✅ **Reproducibility** - Prompts can be reused to generate similar features  
✅ **Documentation** - Prompts serve as development documentation  
✅ **Scalability** - New features can be added by creating new prompts  

### How to View Development Instructions

All architectural and coding guidelines are documented in:
- **`.github/copilot-instructions.md`** - Comprehensive style guide and architecture standards

## 📋 Prerequisites

- **Java 17** or higher
- **Gradle 8.0** or higher (wrapper included)
- **Git** (for cloning)

## 🛠️ Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd demo-copilot-with-sql-h2
```

### 2. Build the Project
```bash
./gradlew build
```

### 3. Run the Application
```bash
./gradlew bootRun
```

The application will start on **http://localhost:8080**

### 4. Run Tests
```bash
./gradlew test
```

### 5. View Test Report
```bash
./gradlew test --info
```

## 🔌 API Endpoints

All endpoints use JSON for request/response bodies.

### Base URL
```
http://localhost:8080/api/products
```

### Endpoints

#### Create Product
```http
POST /api/products
Content-Type: application/json

{
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "quantity": 10
}

Response: 201 Created
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "quantity": 10
}
```

#### Get All Products
```http
GET /api/products

Response: 200 OK
[
  {
    "id": 1,
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "quantity": 10
  }
]
```

#### Get Product by ID
```http
GET /api/products/{id}

Response: 200 OK
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "quantity": 10
}
```

#### Update Product
```http
PUT /api/products/{id}
Content-Type: application/json

{
  "name": "Updated Laptop",
  "description": "Updated description",
  "price": 899.99,
  "quantity": 15
}

Response: 200 OK
{
  "id": 1,
  "name": "Updated Laptop",
  "description": "Updated description",
  "price": 899.99,
  "quantity": 15
}
```

#### Delete Product
```http
DELETE /api/products/{id}

Response: 204 No Content
```

### Error Responses
All errors follow a consistent format:
```json
{
  "timestamp": "2024-01-01T12:00:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: name is required",
  "path": "/api/products"
}
```

## 📬 Postman Collection

A Postman collection with pre-configured requests is available in the `postman-collections/` directory. Import it into Postman to quickly test all API endpoints.

## 🧪 Testing

The project includes comprehensive unit tests:

### Service Tests
- `ProductServiceTest.java` - Tests business logic
- Covers CRUD operations and edge cases
- Uses mocking for repository interactions

### Controller Tests
- `ProductControllerTest.java` - Tests REST endpoints
- Verifies HTTP status codes and response bodies
- Tests validation and error handling

### Run Tests
```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests ProductServiceTest

# Run tests with coverage report
./gradlew test jacocoTestReport
```

## 📦 Dependencies

Key dependencies and their purposes:

| Dependency | Purpose |
|------------|---------|
| `spring-boot-starter-data-jpa` | ORM and database abstraction |
| `spring-boot-starter-web` | REST controller and web support |
| `spring-boot-starter-validation` | Bean Validation (Jakarta) |
| `modelmapper` | Object-to-object mapping |
| `h2` | In-memory relational database |
| `lombok` | Reduce boilerplate code |
| `spring-boot-starter-test` | Testing framework (JUnit 5) |

## 🎓 Learning Resources

This project demonstrates:

1. **Spring Boot Best Practices** - Proper use of @RestController, @Service, @Repository annotations
2. **Layered Architecture** - Clear separation between controller, service, and repository layers
3. **Data Transfer Objects (DTOs)** - Using DTOs for API contracts
4. **Domain-Driven Design** - Domain models separate from entities and DTOs
5. **Object Mapping** - Using ModelMapper for entity-to-DTO conversions
6. **Validation** - Bean Validation annotations with global error handling
7. **Testing** - Unit tests with mocking and assertions
8. **Spring Data JPA** - Leveraging Spring's data access abstractions
9. **Logging** - Structured logging with SLF4J
10. **AI-Assisted Development** - Using prompts to guide code generation

## 🔧 Configuration

### Application Properties
Edit `src/main/resources/application.properties` to customize:
- Server port (default: 8080)
- Database configuration (H2 defaults)
- Logging levels

### H2 Console
Access the H2 database console during development:
```
http://localhost:8080/h2-console
```

Credentials:
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (leave empty)

## 🚀 Next Steps & Extensions

This template can be extended with:

- **Authentication & Authorization** - Spring Security integration
- **API Documentation** - Springdoc OpenAPI / Swagger UI
- **Caching** - Spring Cache or Redis integration
- **Async Processing** - @Async and CompletableFuture
- **Event-Driven Architecture** - Message brokers (RabbitMQ, Kafka)
- **Microservices** - Spring Cloud for distributed systems
- **Database Migrations** - Flyway or Liquibase
- **Monitoring & Metrics** - Spring Boot Actuator & Micrometer
- **GraphQL** - GraphQL API instead of REST

## 📄 License

This project is provided as-is for educational and demonstration purposes.

## 🤝 Contributing

1. Follow the architectural patterns documented in `.github/copilot-instructions.md`
2. Use the prompts in `.github/prompts/` as examples for generating new features
3. Ensure all code follows the layered architecture
4. Add unit tests for all new features
5. Update documentation when adding new endpoints

## 📞 Support

For questions or issues:
1. Review `.github/copilot-instructions.md` for architectural guidelines
2. Check the test cases in `src/test/` for usage examples
3. Refer to the Postman collection for API endpoint examples

---

**Built with ❤️ using GitHub Copilot | Spring Boot 3.5.14 | Java 17**
