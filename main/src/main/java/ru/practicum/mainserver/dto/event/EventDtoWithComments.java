package ru.practicum.mainserver.dto.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventDtoWithComments extends EventFullDto {
    private List<CommentDto> comments;
}
