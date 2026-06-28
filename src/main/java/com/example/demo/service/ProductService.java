package com.example.demo.service;

import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.entity.ProductEntity;
import com.example.demo.service.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Product operations.
 * Contains business logic for creating, reading, updating, and deleting products.
 * Uses ModelMapper for converting between Product entity and domain classes.
 */
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Create a new product.
     *
     * @param product the product domain object to create
     * @return the created product with generated id
     */
    @Transactional
    public Product createProduct(Product product) {
        log.info("Starting createProduct method with product name: {}", product.getName());
        
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        ProductEntity savedEntity = productRepository.save(productEntity);
        Product result = modelMapper.map(savedEntity, Product.class);
        
        log.info("Completed createProduct method, product id: {}", result.getId());
        return result;
    }

    /**
     * Retrieve a product by id.
     *
     * @param id the product id
     * @return the product if found
     * @throws IllegalArgumentException if product not found
     */
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        log.info("Starting getProductById method with id: {}", id);
        
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new IllegalArgumentException("Product not found with id: " + id);
                });
        Product result = modelMapper.map(productEntity, Product.class);
        
        log.info("Completed getProductById method, product name: {}", result.getName());
        return result;
    }

    /**
     * Retrieve all products.
     *
     * @return list of all products
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.info("Starting getAllProducts method");
        
        List<ProductEntity> productEntities = productRepository.findAll();
        List<Product> result = productEntities.stream()
                .map(entity -> modelMapper.map(entity, Product.class))
                .collect(Collectors.toList());
        
        log.info("Completed getAllProducts method, total products: {}", result.size());
        return result;
    }

    /**
     * Update an existing product.
     *
     * @param id the id of the product to update
     * @param product the updated product data
     * @return the updated product
     * @throws IllegalArgumentException if product not found
     */
    @Transactional
    public Product updateProduct(Long id, Product product) {
        log.info("Starting updateProduct method with id: {}", id);
        
        ProductEntity existingEntity = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new IllegalArgumentException("Product not found with id: " + id);
                });

        existingEntity.setName(product.getName());
        existingEntity.setDescription(product.getDescription());
        existingEntity.setPrice(product.getPrice());
        existingEntity.setQuantity(product.getQuantity());

        ProductEntity updatedEntity = productRepository.save(existingEntity);
        Product result = modelMapper.map(updatedEntity, Product.class);
        
        log.info("Completed updateProduct method, product id: {}", result.getId());
        return result;
    }

    /**
     * Delete a product by id.
     *
     * @param id the id of the product to delete
     * @throws IllegalArgumentException if product not found
     */
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Starting deleteProduct method with id: {}", id);
        
        if (!productRepository.existsById(id)) {
            log.error("Product not found with id: {}", id);
            throw new IllegalArgumentException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
        log.info("Completed deleteProduct method, product deleted with id: {}", id);
    }
}

