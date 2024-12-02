package ru.practicum.mainserver.dto.event;

import ru.practicum.mainserver.dto.participation.ParticipationRequestDto;

import java.util.List;

public record EventRequestStatusUpdateResult(
    List<ParticipationRequestDto> confirmedRequests,
    List<ParticipationRequestDto> rejectedRequests
) {
}
