package com.proxidevcode.spring_react_ecommerce.controllers;

import java.util.List;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductResponse;
import com.proxidevcode.spring_react_ecommerce.mappers.CategoryMapper;
import com.proxidevcode.spring_react_ecommerce.models.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;
import com.proxidevcode.spring_react_ecommerce.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryControllers {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }
    

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest dto) {
        return new ResponseEntity<>(categoryService.createCategory(dto), HttpStatus.CREATED);

}


   
    @PutMapping(value ="/{id}" )
    public Category updateCategory(@RequestBody Category category, @PathVariable Long id){
        Category existingCategory = categoryRepository.findById(id).get();
        existingCategory.setName(category.getName());
        Category savedCategory = categoryRepository.save(existingCategory);
         return savedCategory;
    }

 @  DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id ){
    categoryRepository.deleteById(id);
    return ResponseEntity.noContent().build();
   }

    @GetMapping("/{id}")
    public CategoryResponse getCategory( @PathVariable Long id){
        Category category = categoryRepository.findById(id).get();
        return categoryMapper.mapToDto(category);
    }
}
