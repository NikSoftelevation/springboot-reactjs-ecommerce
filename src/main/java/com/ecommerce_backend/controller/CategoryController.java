package com.ecommerce_backend.controller;

import com.ecommerce_backend.payload.ApiResponse;
import com.ecommerce_backend.payload.CategoryDto;
import com.ecommerce_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {

        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategoryByCategoryId(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") int categoryId) {

        CategoryDto updatedCategory = categoryService.updateCategoryByCategoryId(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> getAllCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(getAllCategories, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryByCategoryId(@PathVariable("categoryId") int categoryId) {
        CategoryDto categoryByCategoryId = categoryService.getCategoryByCategoryId(categoryId);
        return new ResponseEntity<>(categoryByCategoryId, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategoryByCategoryId(@PathVariable("categoryId") int categoryId) {

        categoryService.deleteCategoryByCategoryId(categoryId);

        return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.GONE);
    }
}