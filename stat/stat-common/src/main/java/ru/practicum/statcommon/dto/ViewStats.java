package ru.practicum.statcommon.dto;

public record ViewStats(
        String app,
        String uri,
        Integer hits
) {
}
