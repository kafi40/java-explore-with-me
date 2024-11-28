package ru.practicum.stateserver.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;
import ru.practicum.stateserver.repository.model.NativeEndpointHit;
import ru.practicum.stateserver.repository.model.ShortEndpointHit;
import ru.practicum.stateserver.service.entity.EndpointHit;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndpointHitMapper {
    @Mapping(target = "hits", source = "count")
    ViewStats toDto(ShortEndpointHit shortEndpointHit);

    @Mapping(target = "hits", source = "count")
    ViewStats toDto(NativeEndpointHit nativeEndpointHit);

    ViewStats toDto(EndpointHit endpointHit);

    List<ViewStats> toDtoListNative(List<NativeEndpointHit> endpointHits);

    List<ViewStats> toDtoList(List<ShortEndpointHit> endpointHits);

    @Mapping(target = "createdAt", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EndpointHit toEntity(EndpointHitDtoReq endpointHitDtoReq);

    default Timestamp toTimestamp(String createAt) throws DateTimeParseException {
        return Timestamp.valueOf(
                LocalDateTime.parse(
                        URLDecoder.decode(createAt, StandardCharsets.UTF_8),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        );
    }
}
