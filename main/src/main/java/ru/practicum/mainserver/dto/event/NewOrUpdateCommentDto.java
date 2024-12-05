package ru.practicum.mainserver.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewOrUpdateCommentDto {
    @NotBlank(message = "Комментарий не может быть пустым")
    @Size(min = 1, max = 1000, message = "Комментарий может содержать от 1 до 1000 символов")
    private String text;
}
