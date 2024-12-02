package ru.practicum.mainserver.dto.participation;

import ru.practicum.mainserver.enums.Status;
import lombok.Data;

@Data
public class ParticipationRequestDto {
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private Status status;
}
