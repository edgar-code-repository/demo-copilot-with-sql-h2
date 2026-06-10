---
agent: 'plan'
description: 'Adds the ModelMapper library to the project and configures bean to be used.'
---

# Steps

1. Add the ModelMapper dependency to the build.gradle file.
2. Use a modelmapper version that is compatible with the Spring Boot version used in the project.
3. Create a configuration class to define a ModelMapper bean.
4. Annotate the configuration class with @Configuration to indicate that it contains bean definitions.
5. Define a method in the configuration class that returns a ModelMapper instance.
6. The class should be created in the package where the configuration classes are located "com.example.demo.configuration".