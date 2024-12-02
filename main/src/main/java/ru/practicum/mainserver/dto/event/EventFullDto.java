package ru.practicum.mainserver.dto.event;

import ru.practicum.mainserver.enums.State;
import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.user.UserShortDto;

public record EventFullDto(
    String annotation,
    CategoryDto category,
    Integer confirmedRequests,
    String createdOn,
    String description,
    String eventDate,
    Long id,
    UserShortDto initiator,
    LocationDto location,
    Boolean paid,
    Integer participantLimit,
    String publishedOn,
    Boolean requestModeration,
    State state,
    String title,
    Integer views
    ) {

}
