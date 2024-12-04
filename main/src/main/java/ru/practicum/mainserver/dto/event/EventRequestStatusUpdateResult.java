package ru.practicum.mainserver.dto.event;

import lombok.Data;
import ru.practicum.mainserver.dto.participation.ParticipationRequestDto;

import java.util.List;

@Data
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
