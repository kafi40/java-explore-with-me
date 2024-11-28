package ru.practicum.mainserver.service;

import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto get(Long id);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto create(NewCategoryDto newCategory);

    CategoryDto update(CategoryDto updateCategory);

    void delete(Long categoryId);
}
