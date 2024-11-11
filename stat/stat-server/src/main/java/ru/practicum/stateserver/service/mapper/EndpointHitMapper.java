package ru.practicum.stateserver.service.mapper;

import org.mapstruct.Mapper;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.stateserver.service.entity.EndpointHit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    EndpointHitDtoRes toDto(EndpointHit endpointHit);

    EndpointHit fromDto(EndpointHitDtoReq request);

    List<EndpointHitDtoRes> toDtoList(List<EndpointHit> endpointHits);
}
