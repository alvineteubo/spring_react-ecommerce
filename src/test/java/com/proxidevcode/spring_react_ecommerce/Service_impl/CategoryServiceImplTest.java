package com.proxidevcode.spring_react_ecommerce.Service_impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Collections;

//import org.hibernate.engine.internal.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.mappers.CategoryMapper;
import com.proxidevcode.spring_react_ecommerce.models.Category;
import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;
import com.proxidevcode.spring_react_ecommerce.services.impl.CategoryServiceImpl;

import java.util.Optional;

public class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private CategoryRepository categoryRepository;
    private Category category;
    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;
    private Category existingCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category(1L, "iphone");
        categoryRequest = new CategoryRequest("iphone");
        categoryResponse = new CategoryResponse(1L, "iphone");
        existingCategory = new Category(1L, "iphone");

    }


    @Test 
    public void test_it_should_return_category_List(){
        //Mock calls
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        when(categoryMapper.mapToDto(category)).thenReturn(categoryResponse);

        //  Act
        List<CategoryResponse> categoryResponseList = categoryService.getAllCategories();

        //Assert
        assertNotNull(categoryResponseList);
        assertEquals(categoryResponseList.size(), 1);
        assertEquals(category.getId(), categoryResponseList.getFirst().id());
        assertEquals(category.getName(), categoryResponseList.getFirst().name());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void test_it_should_create_category_with_valid_data(){

        when(categoryMapper.mapToEntity(categoryRequest)).thenReturn(category);
        when (categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.mapToDto(category)).thenReturn(categoryResponse);

        //Act
        CategoryResponse result = categoryService.createCategory(categoryRequest);

        //Assert
        assertNotNull(result);
        assertEquals(categoryRequest.name(), result.name());
        verify(categoryRepository, times(1)).save(category);
        
    

    }


    @Test 
    public void test_it_should_update_category_with_valid_data(){

when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
when(categoryMapper.mapToEntity(categoryRequest)).thenReturn(category);
when(categoryRepository.save(category)).thenReturn(category);
when(categoryMapper.mapToDto(category)).thenReturn(categoryResponse);

//Act
CategoryResponse result = categoryService.updateCategory(1L, categoryRequest);

//Asssert 
assertNotNull(result);
assertEquals(categoryRequest.name(), result.name());
assertEquals(existingCategory.getId(), result.id());

verify(categoryRepository, times(1)).save(category);
verify(categoryRepository, times(1)).findById(1L);
verify(categoryMapper, times(1)).mapToEntity(categoryRequest);
verify(categoryMapper, times(1)).mapToDto(category);

    }
    
@Test
public void test_it_should_find_category_with_id_and_return_categoryResponse(){
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
    when(categoryMapper.mapToDto(category)).thenReturn(categoryResponse);

    //Act
    CategoryResponse result =  categoryService.getCategoryDetail(1L);

    //Assert
    assertNotNull(result);
    assertEquals(categoryResponse.name(), result.name());
    verify(categoryRepository, times(1)).findById(1L);
    verify(categoryMapper, times(1)).mapToDto(category);
}

@Test
public void test_it_should_delete_category_with_valid_data(){

    when(categoryRepository.findById(1l)).thenReturn(Optional.of(category));

    //Act
    categoryService.deleteCategory(1L);

    //Assert
     verify(categoryRepository, times(1)).findById(1L);
     verify(categoryRepository, times(1)).delete(category);
}




}
