package ru.practicum.mainserver.dto.compilation;

import lombok.Data;
import ru.practicum.mainserver.dto.event.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    private List<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
