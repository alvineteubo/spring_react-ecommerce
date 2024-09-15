package com.proxidevcode.spring_react_ecommerce.services;

import org.springframework.data.domain.Page;

import com.proxidevcode.spring_react_ecommerce.dtos.PagedResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductResponse;

public interface ProductService {
    
    PagedResponse<ProductResponse> getAllProducts(int page, int size);
    ProductResponse createProduct(ProductRequest dto);
    ProductResponse updateProduct(long id , ProductRequest dto);
    void DeleteProduct(long id);
    ProductResponse getProduct(long id);
    PagedResponse<ProductResponse> getProductsByCategory(long id, int page, int size);


}
