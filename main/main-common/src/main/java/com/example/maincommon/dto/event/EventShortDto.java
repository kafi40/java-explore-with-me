package com.example.maincommon.dto.event;

import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.user.UserShortDto;

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
