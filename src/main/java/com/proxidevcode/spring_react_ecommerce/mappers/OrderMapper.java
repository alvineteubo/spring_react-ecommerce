package com.proxidevcode.spring_react_ecommerce.mappers;

import java.util.Set;

import com.proxidevcode.spring_react_ecommerce.dtos.OrderRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.OrderResponse;
import com.proxidevcode.spring_react_ecommerce.models.Order;
import com.proxidevcode.spring_react_ecommerce.models.OrderProduct;

public interface OrderMapper {
    Order mapToEntity(OrderRequest dto,  Set<OrderProduct> orderProducts);
    OrderResponse mapToDto(Order entity);
    
} 