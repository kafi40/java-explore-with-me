package ru.practicum.mainserver.dto.event;

import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.user.UserShortDto;

public record EventShortDto(
        String annotation,
        CategoryDto category,
        Integer confirmedRequests,
        String eventDate,
        Long id,
        UserShortDto initiator,
        Boolean paid,
        String title,
        Integer views
) {
}
