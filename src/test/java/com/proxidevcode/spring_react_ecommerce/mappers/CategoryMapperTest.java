package com.proxidevcode.spring_react_ecommerce.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.models.Category;
 public class CategoryMapperTest {
private CategoryMapper categoryMapper;


    @BeforeEach
    void setUp(){
        categoryMapper = new CategoryMapper();
        
    }

@Test
public void it_should_map_categoryRequest_to_category(){
    //Arrange
    CategoryRequest categoryRequest = new CategoryRequest("iphone");

    //Act
    Category response = categoryMapper.mapToEntity(categoryRequest);

    //Assert
    assertNotNull(response);
    assertEquals("iphone", response.getName());
}

@Test
public void it_should_map_category_to_categoryResponse(){
    //Arrange
    Category category = new Category(1L, "iphone");

    //Act
    CategoryResponse categoryResponse = categoryMapper.mapToDto(category);

    //Assert
    assertNotNull(categoryResponse);
    assertEquals(category.getId(), categoryResponse.id());
    assertEquals(category.getName(), categoryResponse.name());
}

@Test
public void test_it_should_not_map_nullEntity_to_dto_and_throw_NullPointerException(){
    //Arrange
    Category category = new Category(1L, "iphone");
    // Act and Assert
    var mesg = assertThrows(NullPointerException.class, ()-> categoryMapper.mapToDto(null));
    
    assertEquals("Entity not found", mesg.getMessage());
}

@Test
public void test_it_should_not_map_nullDto_to_Entity_and_throw_NullPointerException(){
    // Arrange
    CategoryResponse categoryResponse = new CategoryResponse(1L, "iphone");
    var mesg = assertThrows(NullPointerException.class, ()->categoryMapper.mapToEntity(null));
assertEquals("dto not found", mesg.getMessage());

}
    
}
