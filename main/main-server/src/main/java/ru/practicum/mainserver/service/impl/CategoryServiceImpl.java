package ru.practicum.mainserver.service.impl;

import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.category.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.exception.NotFountException;
import ru.practicum.mainserver.repository.CategoryRepository;
import ru.practicum.mainserver.service.CategoryService;
import ru.practicum.mainserver.service.entity.Category;
import ru.practicum.mainserver.service.mapper.CategoryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto get(Long id) {
        log.info("Server main (CategoryService): Try get()");

        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new NotFountException("Server main (CategoryService): Not found category with id: " + id));
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        log.info("Server main (CategoryService): Try getAll()");
        return List.of();
    }

    @Override
    public CategoryDto create(NewCategoryDto newCategory) {
        log.info("Server main (CategoryService): Try create()");
        Category category = categoryMapper.toEntity(newCategory);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(CategoryDto updateCategory) {
        log.info("Server main (CategoryService): Try update()");
        Category category = categoryMapper.toEntity(updateCategory);
        return categoryMapper.toDto(category);
    }

    @Override
    public void delete(Long categoryId) {
        log.info("Server main (CategoryService): Try delete()");
        categoryRepository.deleteById(categoryId);
    }
}
