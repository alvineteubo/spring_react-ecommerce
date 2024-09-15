package com.proxidevcode.spring_react_ecommerce.dtos;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderProductRequest {
    private Long  productId;
    private Integer quantity;
    private Double price;
}
