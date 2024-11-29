package com.example.maincommon.dto.compilation;

import com.example.maincommon.dto.event.EventShortDto;

import java.util.List;

public record CompilationDto(
        List<EventShortDto> events,
        Long id,
        Boolean pinned,
        String title
) {
}
