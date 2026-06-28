package com.example.demo.controller;

import com.example.demo.controller.dto.ProductDto;
import com.example.demo.service.ProductService;
import com.example.demo.service.domain.Product;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Product operations.
 * Handles HTTP requests for CRUD operations on products.
 * Uses @RestController annotation and delegates business logic to the service layer.
 */
@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    /**
     * Create a new product.
     *
     * @param productDto the product DTO with request data
     * @return created product with 201 CREATED status
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Starting createProduct endpoint with product name: {}", productDto.getName());
        
        Product product = modelMapper.map(productDto, Product.class);
        Product createdProduct = productService.createProduct(product);
        ProductDto responseDto = modelMapper.map(createdProduct, ProductDto.class);
        
        log.info("Completed createProduct endpoint, product id: {}", responseDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Retrieve a product by id.
     *
     * @param id the product id
     * @return product data with 200 OK status
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        log.info("Starting getProductById endpoint with id: {}", id);
        
        Product product = productService.getProductById(id);
        ProductDto responseDto = modelMapper.map(product, ProductDto.class);
        
        log.info("Completed getProductById endpoint, product name: {}", responseDto.getName());
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Retrieve all products.
     *
     * @return list of all products with 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info("Starting getAllProducts endpoint");
        
        List<Product> products = productService.getAllProducts();
        List<ProductDto> responseDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        
        log.info("Completed getAllProducts endpoint, total products: {}", responseDtos.size());
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * Update an existing product.
     *
     * @param id the id of the product to update
     * @param productDto the updated product data
     * @return updated product with 200 OK status
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        log.info("Starting updateProduct endpoint with id: {}", id);
        
        Product product = modelMapper.map(productDto, Product.class);
        Product updatedProduct = productService.updateProduct(id, product);
        ProductDto responseDto = modelMapper.map(updatedProduct, ProductDto.class);
        
        log.info("Completed updateProduct endpoint, product id: {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Delete a product by id.
     *
     * @param id the id of the product to delete
     * @return 204 NO CONTENT status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Starting deleteProduct endpoint with id: {}", id);
        
        productService.deleteProduct(id);
        
        log.info("Completed deleteProduct endpoint, product deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}

