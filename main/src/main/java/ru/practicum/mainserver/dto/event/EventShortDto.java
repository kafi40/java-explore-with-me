package ru.practicum.mainserver.dto.event;

import lombok.Data;
import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.user.UserShortDto;

@Data
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;
}
