package ru.practicum.mainserver.controller;

import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.category.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDto> getAll(
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("Server main (CategoryController): Get events with param from={}, size={}", from, size);
        return categoryService.getAll(from, size);
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryDto get(@PathVariable Long categoryId) {
        log.info("Server main (CategoryController): Get events with categoryId={}", categoryId);
        return categoryService.get(categoryId);
    }

    @PostMapping("/admin/categories")
    public CategoryDto create(@RequestBody NewCategoryDto newCategory) {
        log.info("Server main (CategoryController): Create new category {}", newCategory);
        return categoryService.create(newCategory);
    }

    @PatchMapping("/admin/categories/{categoryId}")
    public CategoryDto update(@PathVariable Long categoryId, @RequestBody CategoryDto updatedCategory) {
        log.info("Server main (CategoryController): Update category with id={}, body={}", categoryId, updatedCategory);
        return categoryService.update(updatedCategory);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public void delete(@PathVariable Long categoryId) {
        log.info("Server main (CategoryController): Delete category {}", categoryId);
        categoryService.delete(categoryId);
    }
}
