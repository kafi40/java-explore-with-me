package ru.practicum.statgateway.client.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.statgateway.client.EndpointHitClient;

import java.util.List;

@Service
@Slf4j
public class EndpointHitClientImpl implements EndpointHitClient {
    @Value("${stat-server.url}")
    private String serverUrl;
    private final RestClient restClient = RestClient.create(serverUrl);

    @Override
    public List<EndpointHitDtoRes> get(HttpServletRequest request, ) {
        log.info("Gateway stat (client): Try to make request by uri={}", request.getRequestURI());
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/")
                        .queryParam("start", ))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public EndpointHitDtoRes create(EndpointHitDtoReq body, HttpServletRequest request) {
        log.info("Gateway stat (client): Try to make request by uri={} with body={}", request.getRequestURI(), body);
        return restClient.post()
                .uri(request.getRequestURI())
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(EndpointHitDtoRes.class);
    }
}
