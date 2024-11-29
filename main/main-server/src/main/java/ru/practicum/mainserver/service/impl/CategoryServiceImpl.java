package ru.practicum.mainserver.service.impl;

import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.category.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return categoryRepository.findMany(page).stream()
                .map(categoryMapper::toDto)
                .toList();
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
        Category category = categoryRepository.findById(updateCategory.getId())
                .orElseThrow(() -> new NotFountException(
                        "Server main (CategoryService): Not found category with id: " + updateCategory.getId()));

        if (updateCategory.getName() != null)
            category.setName(updateCategory.getName());

        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public void delete(Long categoryId) {
        log.info("Server main (CategoryService): Try delete()");
        categoryRepository.deleteById(categoryId);
    }
}
