package ru.practicum.statgateway.client;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;

import java.util.List;

public interface EndpointHitClient {
   List<EndpointHitDtoRes> get(HttpServletRequest body);

    EndpointHitDtoRes create(EndpointHitDtoReq body, HttpServletRequest request);
}
