package com.proxidevcode.spring_react_ecommerce.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.junit.ExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Answers.values;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductResponse;
import com.proxidevcode.spring_react_ecommerce.mappers.ProductMapper;
import com.proxidevcode.spring_react_ecommerce.models.Category;
import com.proxidevcode.spring_react_ecommerce.models.Product;
import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;
import com.proxidevcode.spring_react_ecommerce.repositories.ProductRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    private ProductRequest productRequest;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private ProductResponse productResponse;
    private CategoryResponse categoryResponse;
    private Product product;
    private Category category;


    @BeforeEach
void setUp() {
    // Sauvegarder la catégorie avant de créer les produits
    category = categoryRepository.save(new Category(1L, "Category"));
    
    productRequest = ProductRequest.builder()
        .name("Ipad")
        .description("Ipad description")
        .price(100.00)
        .quantity(10)
        .categoryId(category.getId())  // Utiliser l'ID de la catégorie persistée
        .build();

    productResponse = ProductResponse.builder()
        .id(1L)
        .name("Product Name")
        .description("Product Description")
        .category(new CategoryResponse(category.getId(), "Category"))
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
    public void test_it_should_create_Product() throws Exception {
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
    }



    @Test
    public void test_it_should_update_product() throws Exception {
        
        category = categoryRepository.save(category);
        Long productId = productRepository.save(ProductMapper.mapToEntity(productRequest, category)).getId();
        mockMvc.perform(put("/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk());
    }

   

   @Test
public void test_it_should_delete_product() throws Exception {
    
    category = categoryRepository.save(category);
    Product savedProduct = productRepository.save(ProductMapper.mapToEntity(productRequest, category));
    
    mockMvc.perform(delete("/products/{id}", savedProduct.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    ;
}

@Test
public void test_it_should_getAllProducts() throws Exception {
    category = categoryRepository.save(category);

    Product product1 = productRepository.save(Product.builder()
            .name("Ipad")
            .description("Ipad description")
            .price(100.00)
            .quantity(10)
            .category(category)
            .build());

    Product product2 = productRepository.save(Product.builder()
            .name("Laptop")
            .description("Laptop description")
            .price(1500.00)
            .category(category)
            .build());

    mockMvc.perform(get("/products")
            .param("page", "0")
            .param("size", "2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.content[0].name").value(product1.getName()))
            .andExpect(jsonPath("$.content[1].name").value(product2.getName()));
}

@Test
public void test_it_should_get_product() throws Exception {
    
    category = categoryRepository.save(category);

    Product savedProduct = productRepository.save(Product.builder()
            .id(1L)
            .name("Meat")
            .description("Meat description")
            .price(100.00)
            .quantity(2)
            .category(category)
            .build());

    mockMvc.perform(get("/products/{id}", savedProduct.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedProduct.getId()))
            .andExpect(jsonPath("$.name").value(savedProduct.getName()))
            .andExpect(jsonPath("$.description").value(savedProduct.getDescription()))
            .andExpect(jsonPath("$.price").value(savedProduct.getPrice()))
            .andExpect(jsonPath("$.quantity").value(savedProduct.getQuantity()))
            .andExpect(jsonPath("$.category.id").value(savedProduct.getCategory().getId()));
}

@Test
public void test_it_should_getProductByCategory() throws Exception {

    category = categoryRepository.save(category);
    System.out.println("Category ID: " + category.getId());

    Product product1 = productRepository.save(Product.builder()
            .name("Rice")
            .description("Rice description")
            .price(800.00)
            .quantity(2)
            .category(category)
            .build());

    Product product2 = productRepository.save(Product.builder()
            .name("Beans")
            .description("Beans description")
            .price(2000.00)
            .quantity(4)
            .category(category)
            .build());

    mockMvc.perform(get("/products/category/{id}", category.getId())
            .param("page", "0")
            .param("size", "2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.content[0].name").value(product1.getName()))
            .andExpect(jsonPath("$.content[0].id").value(product1.getId()))
            .andExpect(jsonPath("$.content[1].name").value(product2.getName()))
            .andExpect(jsonPath("$.content[1].id").value(product2.getId()));
}
}
