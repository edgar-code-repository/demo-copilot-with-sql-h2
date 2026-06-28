package com.example.demo.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Product domain class representing the business model of a product.
 * This class is used within the service layer for business logic operations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;
}

