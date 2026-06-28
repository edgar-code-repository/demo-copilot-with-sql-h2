package com.example.demo.service;

import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.entity.ProductEntity;
import com.example.demo.service.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProductService.
 * Tests cover all CRUD operations with multiple scenarios including edge cases and error handling.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private ProductEntity testProductEntity;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("A test product")
                .price(new BigDecimal("99.99"))
                .quantity(10)
                .build();

        testProductEntity = ProductEntity.builder()
                .id(1L)
                .name("Test Product")
                .description("A test product")
                .price(new BigDecimal("99.99"))
                .quantity(10)
                .build();
    }

    @Test
    void testCreateProduct_Success() {
        // Arrange
        Product inputProduct = Product.builder()
                .name("New Product")
                .description("A new product")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        ProductEntity inputEntity = ProductEntity.builder()
                .name("New Product")
                .description("A new product")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        ProductEntity savedEntity = ProductEntity.builder()
                .id(2L)
                .name("New Product")
                .description("A new product")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        when(modelMapper.map(inputProduct, ProductEntity.class)).thenReturn(inputEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, Product.class)).thenReturn(
                Product.builder()
                        .id(2L)
                        .name("New Product")
                        .description("A new product")
                        .price(new BigDecimal("49.99"))
                        .quantity(5)
                        .build()
        );

        // Act
        Product result = productService.createProduct(inputProduct);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("New Product", result.getName());
        assertEquals(new BigDecimal("49.99"), result.getPrice());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProductEntity));
        when(modelMapper.map(testProductEntity, Product.class)).thenReturn(testProduct);

        // Act
        Product result = productService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(999L));
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllProducts_Success() {
        // Arrange
        ProductEntity product2 = ProductEntity.builder()
                .id(2L)
                .name("Product 2")
                .description("Second product")
                .price(new BigDecimal("29.99"))
                .quantity(20)
                .build();

        List<ProductEntity> entities = Arrays.asList(testProductEntity, product2);
        
        Product domainProduct2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .description("Second product")
                .price(new BigDecimal("29.99"))
                .quantity(20)
                .build();

        when(productRepository.findAll()).thenReturn(entities);
        when(modelMapper.map(testProductEntity, Product.class)).thenReturn(testProduct);
        when(modelMapper.map(product2, Product.class)).thenReturn(domainProduct2);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Product", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetAllProducts_Empty() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        Product updateInput = Product.builder()
                .name("Updated Product")
                .description("Updated description")
                .price(new BigDecimal("149.99"))
                .quantity(15)
                .build();

        ProductEntity updatedEntity = ProductEntity.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated description")
                .price(new BigDecimal("149.99"))
                .quantity(15)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProductEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(updatedEntity);
        when(modelMapper.map(updatedEntity, Product.class)).thenReturn(
                Product.builder()
                        .id(1L)
                        .name("Updated Product")
                        .description("Updated description")
                        .price(new BigDecimal("149.99"))
                        .quantity(15)
                        .build()
        );

        // Act
        Product result = productService.updateProduct(1L, updateInput);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Product", result.getName());
        assertEquals(new BigDecimal("149.99"), result.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
                productService.updateProduct(999L, testProduct));
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(true);

        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        when(productRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(999L));
        verify(productRepository, times(1)).existsById(999L);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    void testCreateProduct_WithNullDescription() {
        // Arrange
        Product inputProduct = Product.builder()
                .name("Product Without Description")
                .price(new BigDecimal("19.99"))
                .quantity(100)
                .build();

        ProductEntity inputEntity = ProductEntity.builder()
                .name("Product Without Description")
                .price(new BigDecimal("19.99"))
                .quantity(100)
                .build();

        ProductEntity savedEntity = ProductEntity.builder()
                .id(3L)
                .name("Product Without Description")
                .price(new BigDecimal("19.99"))
                .quantity(100)
                .build();

        when(modelMapper.map(inputProduct, ProductEntity.class)).thenReturn(inputEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, Product.class)).thenReturn(
                Product.builder()
                        .id(3L)
                        .name("Product Without Description")
                        .price(new BigDecimal("19.99"))
                        .quantity(100)
                        .build()
        );

        // Act
        Product result = productService.createProduct(inputProduct);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertNull(result.getDescription());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testUpdateProduct_WithZeroQuantity() {
        // Arrange
        Product updateInput = Product.builder()
                .name("Out of Stock Product")
                .description("No longer in stock")
                .price(new BigDecimal("9.99"))
                .quantity(0)
                .build();

        ProductEntity updatedEntity = ProductEntity.builder()
                .id(1L)
                .name("Out of Stock Product")
                .description("No longer in stock")
                .price(new BigDecimal("9.99"))
                .quantity(0)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProductEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(updatedEntity);
        when(modelMapper.map(updatedEntity, Product.class)).thenReturn(
                Product.builder()
                        .id(1L)
                        .name("Out of Stock Product")
                        .description("No longer in stock")
                        .price(new BigDecimal("9.99"))
                        .quantity(0)
                        .build()
        );

        // Act
        Product result = productService.updateProduct(1L, updateInput);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getQuantity());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }
}

