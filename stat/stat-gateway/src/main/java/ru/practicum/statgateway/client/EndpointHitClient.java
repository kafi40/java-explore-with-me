package ru.practicum.statgateway.client;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;

import java.util.List;
import java.util.Map;

public interface EndpointHitClient {
   List<ViewStats> get(HttpServletRequest body, Map<String, String> params, List<String> uris);

    ViewStats create(EndpointHitDtoReq body, HttpServletRequest request);
}
