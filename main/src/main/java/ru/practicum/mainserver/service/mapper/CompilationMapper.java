package ru.practicum.mainserver.service.mapper;

import ru.practicum.mainserver.dto.compilation.CompilationDto;
import ru.practicum.mainserver.dto.compilation.NewCompilationDto;
import ru.practicum.mainserver.dto.compilation.UpdateCompilationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.mainserver.service.entity.Compilation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompilationMapper {
    @Mapping(target = "events", ignore = true)
    Compilation toEntity(NewCompilationDto newCompilation);
    @Mapping(target = "events", ignore = true)
    Compilation toEntity(UpdateCompilationRequest updateCompilation);

    CompilationDto toDto(Compilation compilation);
}
