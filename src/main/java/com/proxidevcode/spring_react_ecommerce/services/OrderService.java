package com.proxidevcode.spring_react_ecommerce.services;

import org.springframework.stereotype.Service;

import com.proxidevcode.spring_react_ecommerce.dtos.OrderRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.OrderResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.PagedResponse;


public interface OrderService  {
    PagedResponse <OrderResponse>  getAllOrders(Integer page, Integer pageSize);
    OrderResponse getOrderById(Long id);
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse updateOrder(OrderRequest OrderRequest, String id);
    OrderResponse deleteOrder(String id);

    
} 
