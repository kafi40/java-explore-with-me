package ru.practicum.mainserver.dto.compilation;

import ru.practicum.mainserver.dto.event.EventShortDto;

import java.util.List;

public record CompilationDto(
        List<EventShortDto> events,
        Long id,
        Boolean pinned,
        String title
) {
}
