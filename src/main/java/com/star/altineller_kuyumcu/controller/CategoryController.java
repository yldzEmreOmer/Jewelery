package com.star.altineller_kuyumcu.controller;

import com.star.altineller_kuyumcu.dto.CategoryDto;
import com.star.altineller_kuyumcu.dto.CategoryDtoIU;
import com.star.altineller_kuyumcu.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDtoIU categoryDtoIU) {
        try {
            logger.info("Received category creation request: {}", categoryDtoIU);
            CategoryDto savedCategory = categoryService.save(categoryDtoIU);
            logger.info("Category created successfully: {}", savedCategory);
            return ResponseEntity.ok(savedCategory);
        } catch (Exception e) {
            logger.error("Error creating category: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDtoIU categoryDtoIU) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDtoIU));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> checkCategoryExists(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.existsByName(name));
    }
}