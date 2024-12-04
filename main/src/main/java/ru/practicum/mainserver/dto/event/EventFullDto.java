package ru.practicum.mainserver.dto.event;

import lombok.Data;
import ru.practicum.mainserver.enums.State;
import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.user.UserShortDto;

@Data
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Integer views;
}
