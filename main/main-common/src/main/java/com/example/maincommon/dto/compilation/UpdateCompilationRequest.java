package com.example.maincommon.dto.compilation;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<Integer> events;
    private Boolean pinned;
    @Size(min = 1, max = 50, message = "Заголовок может содержать от 1 до 50 символов")
    private String title;
}
