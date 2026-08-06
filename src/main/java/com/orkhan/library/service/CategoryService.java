package com.orkhan.library.service;

import com.orkhan.library.dto.CategoryRequestDto;
import com.orkhan.library.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto getCategoryById(Long id);

    CategoryResponseDto saveCategory(CategoryRequestDto request);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto request);

    void deleteCategory(Long id);
}