package ru.practicum.mainserver.dto.event;

import lombok.Data;
import ru.practicum.mainserver.dto.user.UserShortDto;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private UserShortDto author;
    private String createdOn;
    private Boolean isModified;
}
