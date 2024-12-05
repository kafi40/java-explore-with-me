package ru.practicum.mainserver.service.mapper;

import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.category.NewCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.mainserver.service.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "name", source = "name")
    Category toEntity(NewCategoryDto newCategory);

    @Mapping(target = "name", source = "name")
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);
}
