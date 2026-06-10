---
agent: 'plan'
description: 'Generates the standard layers for a Spring Boot application.'
---

# Steps

1. Create the following packages in the project structure:
   - `com.example.demo.controller` for the controller layer.
   - `com.example.demo.controller.dto` for the controller DTOs.
   - `com.example.demo.service` for the service layer.
   - `com.example.demo.service.domain` for the service domain objects.
   - `com.example.demo.repository` for the repository layer.
   - `com.example.demo.repository.entity` for the model layer.
   - `com.example.demo.util` for utility classes.
   
2. Ensure that the packages are created in the correct location within the project structure.
3. Don´t create any Java files within the packages, just create the packages themselves and the package-info.java files if necessary.
4. Also, create the test packages corresponding to the main packages:
   - `com.example.demo.controller` for controller tests.
   - `com.example.demo.service` for service tests.
   - `com.example.demo.repository` for repository tests.
