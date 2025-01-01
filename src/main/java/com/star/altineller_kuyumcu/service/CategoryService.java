package com.star.altineller_kuyumcu.service;

import com.star.altineller_kuyumcu.dto.CategoryDto;
import com.star.altineller_kuyumcu.dto.CategoryDtoIU;
import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDtoIU categoryDtoIU);

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long id);

    CategoryDto updateCategory(Long id, CategoryDtoIU categoryDtoIU);

    void deleteCategory(Long id);

    CategoryDto getCategoryByName(String name);

    boolean existsByName(String name);
}