package ru.practicum.statgateway.client.impl;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.statgateway.client.EndpointHitClient;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EndpointHitClientImpl implements EndpointHitClient {
    @Value("${stat-server.url}")
    private String serverUrl;
    private final RestClient restClient = RestClient.create();

    @Override
    public List<EndpointHitDtoRes> get(HttpServletRequest request, Map<String, String> params, @Nullable List<String> uris) {
        log.info("Gateway stat (client): Try to make request by uri={}", request.getRequestURI());

        URI uri = UriComponentsBuilder.fromUriString(serverUrl)
                .path(request.getRequestURI())
                .queryParam("start", params.get("start"))
                .queryParam("end", params.get("end"))
                .queryParam("unique", params.get("unique"))
                .queryParam("uris", uris)
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public EndpointHitDtoRes create(EndpointHitDtoReq body, HttpServletRequest request) {
        log.info("Gateway stat (client): Try to make request by uri={} with body={}", request.getRequestURI(), body);

        URI uri = UriComponentsBuilder.fromUriString(serverUrl)
                .path(request.getRequestURI())
                .build()
                .toUri();

        return restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(EndpointHitDtoRes.class);
    }
}
