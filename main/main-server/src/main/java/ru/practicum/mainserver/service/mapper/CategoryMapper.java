package ru.practicum.mainserver.service.mapper;

import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.category.NewCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.mainserver.service.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(NewCategoryDto newCategory);

    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);

}
