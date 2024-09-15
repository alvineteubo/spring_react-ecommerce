package com.proxidevcode.spring_react_ecommerce.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.mappers.CategoryMapper;
import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jacksonObjectMapper;
    private CategoryRequest categoryRequest;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;


@BeforeEach
void setUp(){
    categoryRequest = new CategoryRequest("biere");

}



@Test
public void test_it_should_create_Category() throws Exception {
    CategoryRequest categoryRequest = new CategoryRequest("Biere");
     
    mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jacksonObjectMapper.writeValueAsString(categoryRequest))) 
        .andExpect(status().isCreated());
        
}

  
 @Test
public void test_it_should_update_category() throws Exception {
    Long categoryId = categoryRepository.save(categoryMapper.mapToEntity(categoryRequest)).getId();
    CategoryRequest uc = new CategoryRequest("jus");

    mockMvc.perform(put("/categories/{id}", categoryId) 
        .contentType(MediaType.APPLICATION_JSON)       
        .content(jacksonObjectMapper.writeValueAsString(uc)))  
        .andExpect(status().isOk());  
}


 @Test
 public void test_it_should_delete_category() throws Exception {
    Long categoryId = categoryRepository.save(categoryMapper.mapToEntity(categoryRequest)).getId();

    mockMvc.perform(delete("/categories/{id}", categoryId)
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isNoContent());

 }
 @Test
 public void test_it_should_return_all_categories() throws Exception{
    mockMvc.perform(get("/categories")).andExpect(status().isOk());
 }
 
}
