package ru.practicum.statcommon.dto;

public record EndpointHitDtoRes(
        String app,
        String uri,
        Integer hits
) {
}
