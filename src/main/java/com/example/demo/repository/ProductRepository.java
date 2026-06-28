package com.example.demo.repository;

import com.example.demo.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product entity.
 * Extends JpaRepository to provide CRUD operations for ProductEntity.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}

