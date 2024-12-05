package ru.practicum.mainserver.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewUserRequest {
    @NotBlank(message = "Должно быть указано имя")
    @Size(min = 2, max = 250, message = "Имя может содержать от 2 до 250 символов")
    private String name;
    @Email
    @NotBlank(message = "Должно быть указан email")
    @Size(min = 6, max = 254, message = "Email может содержать от 6 до 254 символов")
    private String email;
}
