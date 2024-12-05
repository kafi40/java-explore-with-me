package ru.practicum.mainserver.service.mapper;

import ru.practicum.mainserver.dto.participation.ParticipationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.mainserver.service.entity.Participation;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParticipationMapper {
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "requester", ignore = true)
    @Mapping(target = "created", source = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Participation toEntity(ParticipationRequestDto dto);

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    @Mapping(target = "created", source = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ParticipationRequestDto toDto(Participation entity);

    default Timestamp toTimestamp(String dateTime) throws DateTimeParseException {
        return Timestamp.valueOf(
                LocalDateTime.parse(
                        URLDecoder.decode(dateTime, StandardCharsets.UTF_8),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        );
    }
}
