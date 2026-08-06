package com.orkhan.library.service.impl;

import com.orkhan.library.dto.CategoryRequestDto;
import com.orkhan.library.dto.CategoryResponseDto;
import com.orkhan.library.entity.Category;
import com.orkhan.library.repository.CategoryRepository;
import com.orkhan.library.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryResponseDto(category.getId(), category.getName()))
                .toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        return new CategoryResponseDto(category.getId(), category.getName());
    }

    @Override
    public CategoryResponseDto saveCategory(CategoryRequestDto request) {

        Category category = new Category();
        category.setName(request.getName());

        category = categoryRepository.save(category);

        return new CategoryResponseDto(category.getId(), category.getName());
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto request) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());

        category = categoryRepository.save(category);

        return new CategoryResponseDto(category.getId(), category.getName());
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }
}