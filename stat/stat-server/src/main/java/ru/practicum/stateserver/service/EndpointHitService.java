package ru.practicum.stateserver.service;

import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;

import java.util.List;

public interface EndpointHitService {
    List<ViewStats> getStats(String start, String end);

    List<ViewStats> getStatsByUris(String start, String end, List<String> uris);

    List<ViewStats> getStatsUnique(String start, String end);

    List<ViewStats> getStatsUniqueByUris(String start, String end, List<String> uris);

    ViewStats createHits(EndpointHitDtoReq request);
}
