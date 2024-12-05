package ru.practicum.mainserver.service;

import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto get(Long id);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto create(NewCategoryDto newCategory);

    CategoryDto update(Long categoryId, CategoryDto updateCategory);

    void delete(Long categoryId);
}
