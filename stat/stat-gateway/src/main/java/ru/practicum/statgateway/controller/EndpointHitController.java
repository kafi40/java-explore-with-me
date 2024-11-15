package ru.practicum.statgateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.statgateway.client.EndpointHitClient;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class EndpointHitController {
    private final EndpointHitClient endpointHitClient;

    @GetMapping("/stats")
    public List<EndpointHitDtoRes> getStats(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$") String start,
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$") String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique,
            HttpServletRequest request
    ) {
        log.info("Gateway stat (controller): Get stats with param start={}, end={}, uris={}, unique={}", start, end, uris, unique);

        Map<String, String> params = Map.of("start", start, "end", end, "unique", String.valueOf(unique));
        return endpointHitClient.get(request, params, uris);
    }

    @PostMapping("/hit")
    public EndpointHitDtoRes postHit(@Valid @RequestBody EndpointHitDtoReq body, HttpServletRequest request) {
        log.info("Gateway stat (controller): Create EndpointHit={}", body);
        return endpointHitClient.create(body, request);
    }
}
