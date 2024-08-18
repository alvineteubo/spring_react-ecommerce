package com.proxidevcode.spring_react_ecommerce.repositories;

import com.proxidevcode.spring_react_ecommerce.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
