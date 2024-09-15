package com.proxidevcode.spring_react_ecommerce.mappers;

import org.hibernate.annotations.TimeZoneStorage;
import org.springframework.stereotype.Service;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductResponse;
import com.proxidevcode.spring_react_ecommerce.models.Category;
import com.proxidevcode.spring_react_ecommerce.models.Product;


@Service
public class ProductMapper {

 public  static ProductResponse mapToDto(Product entity){
    if(entity == null){
        throw new NullPointerException("entity can not be null");
    }
   CategoryResponse categoryResponse = new CategoryResponse(entity.getCategory().getId(), entity.getCategory().getName());
    ProductResponse dto = ProductResponse.builder()
    .id(entity.getId())
    .name(entity.getName())
    .description(entity.getDescription())
    .price(entity.getPrice())
    .quantity(entity.getQuantity())
    .category(categoryResponse)
    .build();
    return dto;
    
    }

    public static Product mapToEntity(ProductRequest dto, Category category){
        if(dto == null){
            throw new NullPointerException("dto can not be null");
        }
        if (category == null) {
            throw new NullPointerException("category can not be null");
        }
        Product entity = Product.builder()
        .name(dto.getName())
        .description(dto.getDescription())
        .price(dto.getPrice())
        .quantity(dto.getQuantity())
        .category(category)
        .build();
        return entity;
    }

    

}
