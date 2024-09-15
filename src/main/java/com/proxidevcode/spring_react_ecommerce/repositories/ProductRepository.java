package com.proxidevcode.spring_react_ecommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proxidevcode.spring_react_ecommerce.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
Page<Product> findProductByCategoryId(Long id, Pageable pageable);
    
} 
