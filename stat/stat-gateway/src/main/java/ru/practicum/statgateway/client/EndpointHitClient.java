package ru.practicum.statgateway.client;

import org.springframework.stereotype.Component;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;

import java.util.List;
import java.util.Map;

@Component
public interface EndpointHitClient {
   List<ViewStats> get(Map<String, String> params, List<String> uris);

    ViewStats create(EndpointHitDtoReq body);
}
