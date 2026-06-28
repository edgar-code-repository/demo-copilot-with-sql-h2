package com.example.demo.controller.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Product.
 * Used for receiving and returning product data in HTTP requests and responses.
 * Includes bean validation annotations to enforce data constraints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters")
    private String name;

    @Size(max = 500, message = "Product description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "Product price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Product price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal price;

    @NotNull(message = "Product quantity is required")
    @Min(value = 0, message = "Product quantity must be non-negative")
    private Integer quantity;
}

