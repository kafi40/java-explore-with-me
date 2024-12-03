package ru.practicum.mainserver.factory;

import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.category.NewCategoryDto;
import ru.practicum.mainserver.dto.user.NewUserRequest;

import java.util.ArrayList;
import java.util.List;

public class ModelFactory {
    public static CategoryDto createCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Category 1");
        return categoryDto;
    }

    public static List<CategoryDto> createCategoryDtoList(int size) {
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
        newCategoryDto.setName("Test Category " + Math.random() * 1000);
        return newCategoryDto;
    }

    public static NewUserRequest createNewUserRequest() {
        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setName("TestUserName" + Math.random() * 1000);
        newUserRequest.setEmail(newUserRequest.getName() + "@test.com");
        return newUserRequest;
    }
}
