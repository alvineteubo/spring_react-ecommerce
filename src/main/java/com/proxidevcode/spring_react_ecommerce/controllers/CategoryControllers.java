package com.proxidevcode.spring_react_ecommerce.controllers;

import java.util.List;
import com.proxidevcode.spring_react_ecommerce.models.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryControllers {
    private final CategoryRepository categoryRepository;

    
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryRepository.save(category);
    }

    @PutMapping(value ="/{id}" )
    public Category updateCategory(@RequestBody Category category, @PathVariable Long id){
        Category existingCategory = categoryRepository.findById(id).get();
        existingCategory.setName(category.getName());
        Category savedCategory = categoryRepository.save(existingCategory);
        return savedCategory;
    }

@DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteCategory(@PathVariable Long id ){
    categoryRepository.deleteById(id);
    return ResponseEntity.noContent().build();
   }

    @GetMapping("/{name}")
    public String getCategory( @PathVariable String name){
        return "Category " + name;
    }
}
