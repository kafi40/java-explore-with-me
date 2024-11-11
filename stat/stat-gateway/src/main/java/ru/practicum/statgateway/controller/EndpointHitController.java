package ru.practicum.statgateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class EndpointHitController {

    @GetMapping("/stats")
    public List<EndpointHitDtoRes> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique
    ) {
        log.info("Gateway stat (controller): Get stats with param start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return null;
    }

    @PostMapping("/hit")
    public EndpointHitDtoRes postHit(@RequestBody EndpointHitDtoReq request) {
        log.info("Gateway stat (controller): Create EndpointHit={}", request);
        return null;
    }
}
