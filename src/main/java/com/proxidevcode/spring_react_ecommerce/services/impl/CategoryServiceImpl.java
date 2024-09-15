package com.proxidevcode.spring_react_ecommerce.services.impl;

import static com.proxidevcode.spring_react_ecommerce.utils.AppConstants.CATEGORY;
import static com.proxidevcode.spring_react_ecommerce.utils.AppConstants.ID;

import java.util.List;
import com.proxidevcode.spring_react_ecommerce.models.Category;

import org.springframework.stereotype.Service;
import java.util.Optional;

import com.proxidevcode.spring_react_ecommerce.dtos.CategoryRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.CategoryResponse;
import com.proxidevcode.spring_react_ecommerce.exceptions.ResourceNotFoundException;
import com.proxidevcode.spring_react_ecommerce.mappers.CategoryMapper;
import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;
import com.proxidevcode.spring_react_ecommerce.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
     private final CategoryRepository categoryRepository;
     private final CategoryMapper  mapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
      List<Category> categories = categoryRepository.findAll();
      return categories.stream().map(mapper::mapToDto).toList();

    }

    @Override
    public CategoryResponse getCategoryDetail(Long id) {
        Category category = categoryRepository.findById(id).get();
        return mapper.mapToDto(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest dto) {
      Category category = mapper.mapToEntity(dto);
      Category savedCat = categoryRepository.save(category);
      return mapper.mapToDto(savedCat);

    }

    @Override
public CategoryResponse updateCategory(Long id, CategoryRequest dto) {
    Category existingCategory = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(CATEGORY, ID, id));    
    Category category = mapper.mapToEntity(dto);
    category.setId(existingCategory.getId());
    return mapper.mapToDto(categoryRepository.save(category));
};


    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(CATEGORY, ID, id));

        categoryRepository.delete(category); 
    };

}