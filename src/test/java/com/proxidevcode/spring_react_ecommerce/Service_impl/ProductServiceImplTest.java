package com.proxidevcode.spring_react_ecommerce.Service_impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.PrivateKey;
import java.util.Optional;
import java.util.List;
import java.util.Collections;
import org.springframework.data.domain.Page;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;


import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.PagedResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductResponse;
import com.proxidevcode.spring_react_ecommerce.mappers.ProductMapper;
import com.proxidevcode.spring_react_ecommerce.models.Category;
import com.proxidevcode.spring_react_ecommerce.models.Product;
import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;
import com.proxidevcode.spring_react_ecommerce.repositories.ProductRepository;
import com.proxidevcode.spring_react_ecommerce.services.ProductService;
import com.proxidevcode.spring_react_ecommerce.services.impl.ProductServiceImpl;

public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl  productService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;

    private Category category;
    private Product product;
    private ProductResponse productResponse;
    private ProductRequest productRequest;
    private CategoryResponse categoryResponse;




    @BeforeEach
    void SetUp(){
     MockitoAnnotations.openMocks(this);


        category = new Category(1L, "Category");
        categoryResponse = new CategoryResponse(1L, "Category");
        productRequest =  ProductRequest.builder()
        .name("Product Name")
        .description("Product Description")
        .categoryId(1L)
        .price(1223.0)
        .quantity(23)
        .build();

        productResponse = ProductResponse.builder()
         .id(1L)
        .name("Product Name")
        .description("Product Description")
        .category(categoryResponse)
        .price(1223.0)
        .quantity(23)
        .build();

        product = Product.builder()
        .id(1L)
        .name("Product Name")
        .description("Product Description")
        .category(category)
        .price(1223.0)
        .quantity(23)
        .build();
    }


    @Test
    public void test_it_should_createProduct_with_valid_data_and_return_ProductResponse(){
        //Mock calls
        when(categoryRepository.findById(productRequest.getCategoryId())).thenReturn(Optional.of(category));

  try(MockedStatic<ProductMapper> mockedStatic = Mockito.mockStatic(ProductMapper.class)){
    mockedStatic.when(()->ProductMapper.mapToEntity(productRequest, category)).thenReturn(product);
    mockedStatic.when(()->ProductMapper.mapToDto(product)).thenReturn(productResponse); 
    when(productRepository.save(product)).thenReturn(product);
    
        //Act
        ProductResponse response = productService.createProduct(productRequest);

        //assert
        assertNotNull(response);
        assertEquals(productRequest.getName(), response.getName());
        assertEquals(productRequest.getDescription(), response.getDescription());
        assertEquals(productRequest.getPrice(), response.getPrice());
        assertEquals(productRequest.getQuantity(), response.getQuantity());
        assertEquals(productRequest.getCategoryId(), response.getCategory().id());
        

        verify(productRepository, times(1)).save(product);
        verify(categoryRepository, times(1)).findById(1L);
    }
    }


    @Test
    public void test_it_should_update_product_with_valid_data(){

      productRequest.setName("Battery"); 
      productRequest.setDescription("Battery description"); 
      productRequest.setPrice(1500.00);
      productRequest.setQuantity(50);

      //Mocks call

      when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
      when(productRepository.save(product)).thenReturn(product);

     //Act

ProductResponse result= productService.updateProduct(product.getId(), productRequest);

//Asssert
assertNotNull(result);
assertEquals(productRequest.getName(), result.getName());
assertEquals(productRequest.getDescription(), result.getDescription());
assertEquals(productRequest.getPrice(), result.getPrice());
assertEquals(productRequest.getQuantity(), result.getQuantity());

verify(productRepository, times(1)).findById(product.getId());
verify(productRepository, times(1)).save(product);

    }


    @Test
    public void test_it_should_get_All_Products_with_valid_data_and_pagination() {
        List<Product> products = Collections.singletonList(product);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(0, 1), 1);
    
        when(productRepository.findAll(PageRequest.of(0, 1))).thenReturn(productPage);
         try(MockedStatic<ProductMapper> mockedStatic = Mockito.mockStatic(ProductMapper.class)){
            mockedStatic.when(()->ProductMapper.mapToEntity(productRequest, category)).thenReturn(product);
            mockedStatic.when(()->ProductMapper.mapToDto(product)).thenReturn(productResponse); 
            when(productRepository.save(product)).thenReturn(product);
        // Act
        PagedResponse<ProductResponse> result = productService.getAllProducts(0, 1);
    
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElement());
        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getNumber());
        assertEquals(productResponse, result.getContent().get(0));
    
        verify(productRepository).findAll(PageRequest.of(0, 1));
    }
}    


@Test
public void test_it_should_delete_product_and_return_void(){
//mock
    when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

    //Act
    productService.DeleteProduct(product.getId());

    //verify
    verify(productRepository, times(1)).findById(product.getId());
    verify(productRepository, times(1)).delete(product);
}


@Test 
public void test_it_should_get_products_by_category_with_valid_data(){
List<Product> products = Collections.singletonList(product);
Page<Product> productPage = new PageImpl<>(products, PageRequest.of(0,1), 1);

when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
when(productRepository.findProductByCategoryId(category.getId(), PageRequest.of(0, 1))).thenReturn(productPage);


try(MockedStatic<ProductMapper> mockedStatic = Mockito.mockStatic(ProductMapper.class)){
    mockedStatic.when(()->ProductMapper.mapToDto(product)).thenReturn(productResponse);


//Act

PagedResponse<ProductResponse> result = productService.getProductsByCategory(category.getId(), 0, 1);

//Assert
assertNotNull(result);
assertEquals(1, result.getNumberOfElements());
assertEquals(1, result.getTotalPages());
assertEquals(0, result.getNumber());
assertEquals(productResponse, result.getContent().get(0));

verify(categoryRepository, times(1)).findById(category.getId());
verify(productRepository, times(1)).findProductByCategoryId(category.getId(), PageRequest.of(0, 1));

}

}
}