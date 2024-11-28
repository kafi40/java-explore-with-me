package com.example.maincommon.dto.event;

import com.example.maincommon.dto.Location;
import com.example.maincommon.dto.StateAction;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000, message = "Аннотация может содержать от 20 до 2000 символов")
    private String annotation;
    @Positive(message = "ID категории не может быть меньше 1")
    private Long category;
    @Size(min = 20, max = 7000, message = "Описание может содержать от 20 до 7000 символов")
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    @PositiveOrZero(message = "Лимит не можеть быть меньше 0")
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120, message = "Заголовок может содержать от 3 до 120 символов")
    private String title;
}
