package com.example.demo.controller;

import com.example.demo.controller.dto.ProductDto;
import com.example.demo.service.ProductService;
import com.example.demo.service.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for ProductController.
 * Tests REST endpoints with edge cases and error handling.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto testProductDto;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("A test product")
                .price(new BigDecimal("99.99"))
                .quantity(10)
                .build();

        testProductDto = ProductDto.builder()
                .id(1L)
                .name("Test Product")
                .description("A test product")
                .price(new BigDecimal("99.99"))
                .quantity(10)
                .build();
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        // Arrange
        ProductDto inputDto = ProductDto.builder()
                .name("New Product")
                .description("A new product")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        Product inputProduct = Product.builder()
                .name("New Product")
                .description("A new product")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        Product createdProduct = Product.builder()
                .id(2L)
                .name("New Product")
                .description("A new product")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        ProductDto createdDto = ProductDto.builder()
                .id(2L)
                .name("New Product")
                .description("A new product")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        when(modelMapper.map(inputDto, Product.class)).thenReturn(inputProduct);
        when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);
        when(modelMapper.map(createdProduct, ProductDto.class)).thenReturn(createdDto);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("New Product")))
                .andExpect(jsonPath("$.price", is(49.99)))
                .andExpect(jsonPath("$.quantity", is(5)));
    }

    @Test
    void testCreateProduct_InvalidData_MissingName() throws Exception {
        // Arrange
        ProductDto invalidDto = ProductDto.builder()
                .description("A product without name")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateProduct_InvalidData_NegativePrice() throws Exception {
        // Arrange
        ProductDto invalidDto = ProductDto.builder()
                .name("Invalid Product")
                .price(new BigDecimal("-10.00"))
                .quantity(5)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateProduct_InvalidData_ZeroPrice() throws Exception {
        // Arrange
        ProductDto invalidDto = ProductDto.builder()
                .name("Invalid Product")
                .price(BigDecimal.ZERO)
                .quantity(5)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateProduct_InvalidData_NegativeQuantity() throws Exception {
        // Arrange
        ProductDto invalidDto = ProductDto.builder()
                .name("Invalid Product")
                .price(new BigDecimal("49.99"))
                .quantity(-5)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetProductById_Success() throws Exception {
        // Arrange
        when(productService.getProductById(1L)).thenReturn(testProduct);
        when(modelMapper.map(testProduct, ProductDto.class)).thenReturn(testProductDto);

        // Act & Assert
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Product")))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.quantity", is(10)));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        // Arrange
        when(productService.getProductById(999L))
                .thenThrow(new IllegalArgumentException("Product not found with id: 999"));

        // Act & Assert
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllProducts_Success() throws Exception {
        // Arrange
        Product product2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .description("Second product")
                .price(new BigDecimal("29.99"))
                .quantity(20)
                .build();

        ProductDto productDto2 = ProductDto.builder()
                .id(2L)
                .name("Product 2")
                .description("Second product")
                .price(new BigDecimal("29.99"))
                .quantity(20)
                .build();

        List<Product> products = Arrays.asList(testProduct, product2);

        when(productService.getAllProducts()).thenReturn(products);
        when(modelMapper.map(testProduct, ProductDto.class)).thenReturn(testProductDto);
        when(modelMapper.map(product2, ProductDto.class)).thenReturn(productDto2);

        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test Product")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Product 2")));
    }

    @Test
    void testGetAllProducts_Empty() throws Exception {
        // Arrange
        when(productService.getAllProducts()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        // Arrange
        ProductDto updateDto = ProductDto.builder()
                .name("Updated Product")
                .description("Updated description")
                .price(new BigDecimal("149.99"))
                .quantity(15)
                .build();

        Product updateProduct = Product.builder()
                .name("Updated Product")
                .description("Updated description")
                .price(new BigDecimal("149.99"))
                .quantity(15)
                .build();

        Product updatedProduct = Product.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated description")
                .price(new BigDecimal("149.99"))
                .quantity(15)
                .build();

        ProductDto updatedDto = ProductDto.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated description")
                .price(new BigDecimal("149.99"))
                .quantity(15)
                .build();

        when(modelMapper.map(updateDto, Product.class)).thenReturn(updateProduct);
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);
        when(modelMapper.map(updatedProduct, ProductDto.class)).thenReturn(updatedDto);

        // Act & Assert
        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.price", is(149.99)));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        // Arrange
        ProductDto updateDto = ProductDto.builder()
                .name("Updated Product")
                .price(new BigDecimal("149.99"))
                .quantity(15)
                .build();

        Product updateProduct = Product.builder()
                .name("Updated Product")
                .price(new BigDecimal("149.99"))
                .quantity(15)
                .build();

        when(modelMapper.map(updateDto, Product.class)).thenReturn(updateProduct);
        when(productService.updateProduct(eq(999L), any(Product.class)))
                .thenThrow(new IllegalArgumentException("Product not found with id: 999"));

        // Act & Assert
        mockMvc.perform(put("/api/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        // Arrange
        doNothing().when(productService).deleteProduct(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void testDeleteProduct_NotFound() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("Product not found with id: 999"))
                .when(productService).deleteProduct(999L);

        // Act & Assert
        mockMvc.perform(delete("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct_ValidData_WithoutDescription() throws Exception {
        // Arrange
        ProductDto inputDto = ProductDto.builder()
                .name("Product Without Description")
                .price(new BigDecimal("19.99"))
                .quantity(100)
                .build();

        Product inputProduct = Product.builder()
                .name("Product Without Description")
                .price(new BigDecimal("19.99"))
                .quantity(100)
                .build();

        Product createdProduct = Product.builder()
                .id(3L)
                .name("Product Without Description")
                .price(new BigDecimal("19.99"))
                .quantity(100)
                .build();

        ProductDto createdDto = ProductDto.builder()
                .id(3L)
                .name("Product Without Description")
                .price(new BigDecimal("19.99"))
                .quantity(100)
                .build();

        when(modelMapper.map(inputDto, Product.class)).thenReturn(inputProduct);
        when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);
        when(modelMapper.map(createdProduct, ProductDto.class)).thenReturn(createdDto);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Product Without Description")));
    }

    @Test
    void testUpdateProduct_InvalidData_ZeroQuantity() throws Exception {
        // Arrange
        ProductDto updateDto = ProductDto.builder()
                .name("Out of Stock Product")
                .description("No longer in stock")
                .price(new BigDecimal("9.99"))
                .quantity(0)
                .build();

        Product updateProduct = Product.builder()
                .name("Out of Stock Product")
                .description("No longer in stock")
                .price(new BigDecimal("9.99"))
                .quantity(0)
                .build();

        Product updatedProduct = Product.builder()
                .id(1L)
                .name("Out of Stock Product")
                .description("No longer in stock")
                .price(new BigDecimal("9.99"))
                .quantity(0)
                .build();

        ProductDto updatedDto = ProductDto.builder()
                .id(1L)
                .name("Out of Stock Product")
                .description("No longer in stock")
                .price(new BigDecimal("9.99"))
                .quantity(0)
                .build();

        when(modelMapper.map(updateDto, Product.class)).thenReturn(updateProduct);
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);
        when(modelMapper.map(updatedProduct, ProductDto.class)).thenReturn(updatedDto);

        // Act & Assert
        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(0)));
    }
}





