package com.example.maincommon.dto.event;

import com.example.maincommon.dto.Location;
import com.example.maincommon.dto.State;
import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.user.UserShortDto;

public record EventFullDto(
    String annotation,
    CategoryDto category,
    Integer confirmedRequests,
    String createdOn,
    String description,
    String eventDate,
    Long id,
    UserShortDto initiator,
    Location location,
    Boolean paid,
    Integer participantLimit,
    String publishedOn,
    Boolean requestModeration,
    State state,
    String title,
    Integer views
    ) {

}
