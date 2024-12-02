package ru.practicum.mainserver.controller;

import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.category.NewCategoryDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDto> getAll(
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("Server main (CategoryController): Get events with param from={}, size={}", from, size);
        return categoryService.getAll(from, size);
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryDto get(@Positive @PathVariable Long categoryId) {
        log.info("Server main (CategoryController): Get events with categoryId={}", categoryId);
        return categoryService.get(categoryId);
    }

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody NewCategoryDto newCategory) {
        log.info("Server main (CategoryController): Create new category {}", newCategory);
        return categoryService.create(newCategory);
    }

    @PatchMapping("/admin/categories/{categoryId}")
    public CategoryDto update(
            @Positive @PathVariable Long categoryId,
            @Valid @RequestBody CategoryDto updatedCategory) {
        log.info("Server main (CategoryController): Update category with id={}, body={}", categoryId, updatedCategory);
        return categoryService.update(categoryId, updatedCategory);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long categoryId) {
        log.info("Server main (CategoryController): Delete category {}", categoryId);
        categoryService.delete(categoryId);
    }
}
