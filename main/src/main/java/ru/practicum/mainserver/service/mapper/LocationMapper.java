package ru.practicum.mainserver.service.mapper;

import ru.practicum.mainserver.dto.event.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.mainserver.service.entity.Location;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    Location toEntity(LocationDto dto);

    LocationDto toDto(Location entity);
}
