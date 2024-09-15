package com.proxidevcode.spring_react_ecommerce.mappers;

import org.springframework.stereotype.Service;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.models.Category;

@Service
public class CategoryMapper {

    public CategoryResponse mapToDto(Category entity){
        if (entity==null){
            throw new NullPointerException("Entity not found");
        }
        return new CategoryResponse(entity.getId(), entity.getName());
    }

public Category mapToEntity(CategoryRequest dto){
if(dto==null){
    throw new NullPointerException("dto not found");
}
    return Category.builder().name(dto.name()).build();

}

}
