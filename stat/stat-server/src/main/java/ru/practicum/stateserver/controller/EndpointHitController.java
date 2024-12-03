package ru.practicum.stateserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;
import ru.practicum.stateserver.service.EndpointHitService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    @GetMapping("/stats")
    public List<ViewStats> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique
    ) {
        log.info("Server stat (controller): Get stats with param start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        if ((uris != null && !uris.isEmpty()) && unique) {
            return endpointHitService.getStatsUniqueByUris(start, end, uris);
        } else if (uris != null && !uris.isEmpty()) {
            return endpointHitService.getStatsByUris(start, end, uris);
        } else if (unique) {
            return endpointHitService.getStatsUnique(start, end);
        } else {
            return endpointHitService.getStats(start, end);
        }
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewStats postHit(@Valid @RequestBody EndpointHitDtoReq request) {
        log.info("Server stat (controller): Create EndpointHit={}", request);
        return endpointHitService.createHits(request);
    }
}
