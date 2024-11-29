package ru.practicum.mainserver.service;

import com.example.maincommon.dto.EventRequestParam;
import com.example.maincommon.dto.event.*;
import com.example.maincommon.dto.participation.ParticipationRequestDto;

import java.util.List;

public interface EventService {
    List<EventShortDto> getAll(EventRequestParam params);

    EventFullDto get(Long eventId);

    List<EventShortDto> getUserEvents(Long userId, int from, int size);

    EventFullDto getUserEvent(Long userId, Long eventId);

    EventFullDto create(Long userId, NewEventDto newEvent);

    EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest updateEvent);

    List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId);

    List<EventRequestStatusUpdateResult> updateUserEventRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest);

    List<EventFullDto> getAllForAdmin(EventRequestParam params);

    EventFullDto updateForAdmin(Long eventId, UpdateEventUserRequest updateEvent);
}
