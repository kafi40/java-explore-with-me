package ru.practicum.mainserver.service.mapper;

import com.example.maincommon.dto.event.EventFullDto;
import com.example.maincommon.dto.event.EventShortDto;
import com.example.maincommon.dto.event.NewEventDto;
import com.example.maincommon.dto.event.UpdateEventUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.mainserver.service.entity.Event;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    Event toEntity(NewEventDto dto);

    Event toEntity(UpdateEventUserRequest dto);

    EventShortDto toShortDto(Event event);

    EventFullDto toDto(Event event);

}
