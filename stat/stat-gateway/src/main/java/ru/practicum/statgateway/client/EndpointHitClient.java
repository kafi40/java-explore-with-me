package ru.practicum.statgateway.client;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;

import java.util.List;
import java.util.Map;

public interface EndpointHitClient {
   List<EndpointHitDtoRes> get(HttpServletRequest body, Map<String, String> params, List<String> uris);

    EndpointHitDtoRes create(EndpointHitDtoReq body, HttpServletRequest request);
}
