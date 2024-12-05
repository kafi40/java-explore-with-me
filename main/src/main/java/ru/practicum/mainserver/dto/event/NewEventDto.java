package ru.practicum.mainserver.dto.event;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NewEventDto {
    @NotBlank(message = "Должна быть аннотация")
    @Size(min = 20, max = 2000, message = "Аннотация может содержать от 20 до 2000 символов")
    private String annotation;
    @NotNull(message = "Должна быть указана категрия")
    @Positive(message = "ID категории не может быть меньше 1")
    private Long category;
    @NotBlank(message = "Должно быть опиисание")
    @Size(min = 20, max = 7000, message = "Описание может содержать от 20 до 7000 символов")
    private String description;
    @NotBlank(message = "Не указана дата события")
    private String eventDate;
    @NotNull(message = "Не указана локация")
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero(message = "Лимит не можеть быть меньше 0")
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank(message = "Должен быть загаловок")
    @Size(min = 3, max = 120, message = "Заголовок может содержать от 3 до 120 символов")
    private String title;
}
