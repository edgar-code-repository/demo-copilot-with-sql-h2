---
agent: 'plan'
description: 'Generates a complete CRUD implementation for a Product entity, including controllers, services, and database models.'
---

# Steps

1. Add Product entity class to the repository layer.
2. Define the Product entity with appropriate fields such as id, name, description, price, and quantity.
3. Add Product repository interface that extends JpaRepository to handle database operations.
4. Create a Product service class that contains business logic for handling Product operations.
5. Implement methods in the Product service class for creating, reading, updating, and deleting products.
6. The service layer must use a Product domain class that represents the business model of a product.
7. The Product domain class should initially have the same fields as the Product entity.
8. Create a Product controller class that defines REST endpoints for CRUD operations on products.
9. The controller layer should use a Product DTO class to receive and return data from each endpoint.
10. The Product DTO class should also have the same fields as the Product entity.
11. Add Bean Validation annotations on ProductDto and enforce in controller (@Valid).
12. Use ModelMapper to convert between the Product entity, Product domain, and Product Dto classes in the service and controller layers.
13. Implement the controller methods to call the corresponding service methods for each CRUD operation.
14. Ensure that the controller methods return appropriate HTTP responses and status codes.
15. Add logging statements indicating the first and last line of each method in the service and controller classes.
16. Create unit tests for the service and controller classes to verify the functionality of each CRUD operation.
17. Ensure that the unit tests cover all possible scenarios, including edge cases and error handling.
18. Create a Postman collection that includes requests for each CRUD operation in a directory named "postman-collections" in the root of the project.



