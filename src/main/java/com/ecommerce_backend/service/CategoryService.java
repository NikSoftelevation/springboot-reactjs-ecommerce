package com.ecommerce_backend.service;

import com.ecommerce_backend.payload.CategoryDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.PublicKey;
import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto);

    public CategoryDto updateCategoryByCategoryId(@RequestBody CategoryDto categoryDto, int categoryId);

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategoryByCategoryId(int categoryId);

    public void deleteCategoryByCategoryId(int categoryId);
}