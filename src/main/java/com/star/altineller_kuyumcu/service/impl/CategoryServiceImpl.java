package com.star.altineller_kuyumcu.service.impl;

import com.star.altineller_kuyumcu.dto.CategoryDto;
import com.star.altineller_kuyumcu.dto.CategoryDtoIU;
import com.star.altineller_kuyumcu.model.Category;
import com.star.altineller_kuyumcu.repository.CategoryRepository;
import com.star.altineller_kuyumcu.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto save(CategoryDtoIU categoryDtoIU) {
        Category category = new Category();
        category.setCategoryName(categoryDtoIU.getCategoryName());
        category.setImageUrl(categoryDtoIU.getImageUrl());

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDtoIU categoryDtoIU) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setCategoryName(categoryDtoIU.getCategoryName());
        category.setImageUrl(categoryDtoIU.getImageUrl());

        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByCategoryName(name)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + name));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByCategoryName(name);
    }
}