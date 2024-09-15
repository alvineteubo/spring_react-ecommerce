package com.proxidevcode.spring_react_ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxidevcode.spring_react_ecommerce.models.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    
}
