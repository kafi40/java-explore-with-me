package ru.practicum.mainserver.factory;

import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.category.NewCategoryDto;

import java.util.ArrayList;
import java.util.List;

public class ModelFactory {
    public static CategoryDto createCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Category 1");
        return categoryDto;
    }

    public static List<CategoryDto> CreateCategoryDtoList(int size) {
        List<CategoryDto> list = new ArrayList<>();
        for (int i = 1; i < size + 1; i++) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId((long) i);
            categoryDto.setName("Category " + (i));
            list.add(categoryDto);
        }
        return list;
    }

    public static NewCategoryDto createNewCategoryDto() {
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("Category 1");
        return newCategoryDto;
    }
}
