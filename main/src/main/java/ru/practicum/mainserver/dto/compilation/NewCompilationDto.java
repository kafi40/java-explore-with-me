package ru.practicum.mainserver.dto.compilation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned;
    @NotBlank(message = "Должен быть заголовок")
    @Size(min = 1, max = 50, message = "Заголовок может содержать от 1 до 50 символов")
    private String title;
}
