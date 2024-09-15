package com.proxidevcode.spring_react_ecommerce.mappers;

import com.proxidevcode.spring_react_ecommerce.dtos.OrderProductRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.OrderProductResponse;
import com.proxidevcode.spring_react_ecommerce.models.Order;
import com.proxidevcode.spring_react_ecommerce.models.OrderProduct;
import com.proxidevcode.spring_react_ecommerce.models.Product;


public class OrderProductMapper {
     public static OrderProductResponse mapToDto (OrderProduct entity) {
    if(entity== null){  throw new NullPointerException("entity is null");
    }
    return OrderProductResponse.builder()
    .id(entity.getId())
    .productId(entity.getProduct().getId())
    .productName(entity.getProduct().getName())
    .price(entity.getPrice())
    .quantity(entity.getQuantity())
    .build();      
}

public static OrderProduct mapToEntity( OrderProductRequest dto, Order order, Product product ){
    if(dto==null){throw new NullPointerException("dto is null");}


   return OrderProduct.builder()
    .order(order)
    .product(product)
    .quantity(dto.getQuantity())
    .price(dto.getPrice())
    .build();
 }
}
