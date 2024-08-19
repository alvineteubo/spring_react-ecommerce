package com.proxidevcode.spring_react_ecommerce.mappers;

import org.springframework.stereotype.Service;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.models.Category;

@Service
public class CategoryMapper {
    
    public CategoryResponse mapToDto(Category entity){
        return new CategoryResponse(entity.getId(), entity.getName());
    }

public Category mapToEntity(CategoryRequest dto){
    return Category.builder().name(dto.name()).build();

}

}


