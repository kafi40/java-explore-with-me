package ru.practicum.stateserver.service.mapper;

import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.stateserver.service.entity.EndpointHit;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndpointHitMapper {
    public static EndpointHitDtoRes toDto(EndpointHit endpointHit) {
        return new EndpointHitDtoRes(endpointHit.getApp(), endpointHit.getUri(), null);
    }

    public static List<EndpointHitDtoRes> toDtoList(List<EndpointHit> endpointHits) {
        Map<String, EndpointHitDtoRes> views = new HashMap<>();

        endpointHits.forEach(e -> {
                    if (!views.containsKey(e.getUri())) {
                        views.put(e.getUri(), new EndpointHitDtoRes(e.getApp(), e.getUri(), 1));
                    } else {
                        views.put(e.getUri(), new EndpointHitDtoRes(e.getApp(), e.getUri(), views.get(e.getUri()).hits() + 1));
                    }
                });

        return views.values().stream().toList();
    }

    public static EndpointHit fromDto(EndpointHitDtoReq request) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(request.getApp());
        endpointHit.setUri(request.getUri());
        endpointHit.setIp(request.getIp());
        endpointHit.setCreatedAt(Timestamp.valueOf(request.getTimestamp()));
        return endpointHit;
    }
}
