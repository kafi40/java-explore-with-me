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
import ru.practicum.statcommon.dto.ViewStats;
import ru.practicum.statgateway.client.EndpointHitClient;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EndpointHitClientImpl implements EndpointHitClient {
    @Value("${stats-server.url}")
    private String serverUrl;
    private final RestClient restClient = RestClient.create(serverUrl);

    @Override
    public List<ViewStats> get(HttpServletRequest request, Map<String, String> params, @Nullable List<String> uris) {
        log.info("Gateway stat (client): Try to make request by uri={}", request.getRequestURI());

        URI uri = UriComponentsBuilder
                .fromUriString(serverUrl)
                .path(request.getRequestURI())
                .queryParam("start", params.get("start"))
                .queryParam("end", params.get("end"))
                .queryParam("unique", params.get("unique"))
                .queryParam("uris", uris)
                .build()
                .toUri();
        try {
            return restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception e) {
            log.error("Gateway stat (client): Failed to access the server: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ViewStats create(EndpointHitDtoReq body, HttpServletRequest request) {
        log.info("Gateway stat (client): Try to make request by uri={} with body={}", request.getRequestURI(), body);

        URI uri = UriComponentsBuilder.fromUriString(serverUrl)
                .path(request.getRequestURI())
                .build()
                .toUri();

        try {
            return restClient.post()
                    .uri(uri)
                    .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
                    .headers(httpHeaders -> httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON)))
                    .body(body)
                    .retrieve()
                    .body(ViewStats.class);
        } catch (Exception e) {
            log.error("Gateway stat (client): Failed to access the server: {}", e.getMessage());
            return null;
        }
    }
}
