package com.proxidevcode.spring_react_ecommerce.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor@NoArgsConstructor
@Data@Builder
public class OrderResponse {
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String address;
    Set<OrderProductResponse> orderProducts;
}

    

