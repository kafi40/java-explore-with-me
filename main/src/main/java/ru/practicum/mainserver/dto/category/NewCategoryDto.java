package ru.practicum.mainserver.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCategoryDto {
    @NotBlank(message = "Должно быть название")
    @Size(min = 1, max = 50, message = "Название может содержать от 1 до 50 символов")
    private String name;
}
