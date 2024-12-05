package ru.practicum.mainserver.service.mapper;

import ru.practicum.mainserver.dto.event.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.mainserver.service.entity.Event;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    @Mapping(target = "eventDate", source = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "category", ignore = true)
    Event toEntity(NewEventDto dto);

    @Mapping(target = "eventDate", source = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "category", ignore = true)
    Event toEntity(UpdateEvent dto);

    @Mapping(target = "eventDate", source = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EventShortDto toShortDto(Event event);

    @Mapping(target = "eventDate", source = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "createdOn", source = "createdOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "publishedOn", source = "publishedOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EventFullDto toDto(Event event);

    @Mapping(target = "eventDate", source = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "createdOn", source = "createdOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "publishedOn", source = "publishedOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EventDtoWithComments toDtoWithComments(Event event);

    default Timestamp toTimestamp(String dateTime) throws DateTimeParseException {
        return Timestamp.valueOf(
                LocalDateTime.parse(
                        URLDecoder.decode(dateTime, StandardCharsets.UTF_8),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        );
    }

}
