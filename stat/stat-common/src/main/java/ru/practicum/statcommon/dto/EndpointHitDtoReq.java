package ru.practicum.statcommon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EndpointHitDtoReq {
    @Size(max = 50, message = "Превышена длина названия приложения")
    @NotBlank(message = "Должно быть название приложения")
    private String app;
    @Size(max = 255, message = "Превышена длина URI")
    @NotBlank(message = "Должен быть указан URI")
    private String uri;
    @Size(max = 20, message = "Превышена длина IP")
    @NotBlank(message = "Должен быть указан IP")
    private String ip;
    @NotBlank(message = "Должна быть указана дата и время")
    private String timestamp;
}
