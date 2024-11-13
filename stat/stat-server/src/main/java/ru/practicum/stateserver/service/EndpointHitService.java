package ru.practicum.stateserver.service;

import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;

import java.util.List;

public interface EndpointHitService {
    List<EndpointHitDtoRes> getStats(String start, String end);

    List<EndpointHitDtoRes> getStatsByUris(String start, String end, List<String> uris);

    List<EndpointHitDtoRes> getStatsUnique(String start, String end);

    List<EndpointHitDtoRes> getStatsUniqueByUris(String start, String end, List<String> uris);

    EndpointHitDtoRes createHits(EndpointHitDtoReq request);
}
