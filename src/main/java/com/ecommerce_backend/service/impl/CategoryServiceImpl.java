package com.ecommerce_backend.service.impl;

import com.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce_backend.model.Category;
import com.ecommerce_backend.payload.CategoryDto;
import com.ecommerce_backend.repository.CategoryRepository;
import com.ecommerce_backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        /*CategoryDto to Category*/
        Category mappedCategory = modelMapper.map(categoryDto, Category.class);
        Category save = categoryRepository.save(mappedCategory);

        /*Category to CategoryDto*/
        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategoryByCategoryId(CategoryDto categoryDto, int categoryId) {

        Category categoryToUpdate = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("No Category found with categoryId : " + categoryId));

        categoryToUpdate.setTitle(categoryDto.getTitle());
        Category save = categoryRepository.save(categoryToUpdate);

        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> findAll = categoryRepository.findAll();

        List<CategoryDto> allDto = findAll.stream().map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return allDto;
    }

    @Override
    public CategoryDto getCategoryByCategoryId(int categoryId) {

        Category categoryByCategoryId = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("No Category found with categoryId : " + categoryId));

        return modelMapper.map(categoryByCategoryId, CategoryDto.class);
    }

    @Override
    public void deleteCategoryByCategoryId(int categoryId) {

        Category categoryToDelete = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("No Category found with categoryId : " + categoryId));

        categoryRepository.save(categoryToDelete);
    }
}