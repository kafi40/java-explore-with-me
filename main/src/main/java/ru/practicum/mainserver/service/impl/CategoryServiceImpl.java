package ru.practicum.mainserver.service.impl;

import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.category.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.exception.NotFountException;
import ru.practicum.mainserver.repository.CategoryRepository;
import ru.practicum.mainserver.service.CategoryService;
import ru.practicum.mainserver.service.entity.Category;
import ru.practicum.mainserver.service.mapper.CategoryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public CategoryDto get(Long id) {
        log.info("Server main (CategoryService): Try get()");
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new NotFountException("Server main (CategoryService): Not found category with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAll(int from, int size) {
        log.info("Server main (CategoryService): Try getAll()");
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return categoryRepository.findAll(page).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto create(NewCategoryDto newCategory) {
        log.info("Server main (CategoryService): Try create()");
        Category category = categoryMapper.toEntity(newCategory);
        try {
            category = categoryRepository.save(category);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Данное имя для категории уже используется");
        }
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long categoryId, CategoryDto updateCategory) {
        log.info("Server main (CategoryService): Try update()");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFountException(
                        "Server main (CategoryService): Not found category with id: " + categoryId));

        if (updateCategory.getName() != null)
            category.setName(updateCategory.getName());

        try {
            category = categoryRepository.save(category);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Данное имя для категории уже используется");
        }
        return categoryMapper.toDto(category);
    }

    @Override
    public void delete(Long categoryId) {
        log.info("Server main (CategoryService): Try delete()");
        categoryRepository.deleteById(categoryId);
    }
}
