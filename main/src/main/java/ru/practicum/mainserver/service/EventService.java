package ru.practicum.mainserver.service;

import ru.practicum.mainserver.utils.EventRequestParam;
import ru.practicum.mainserver.dto.event.*;
import ru.practicum.mainserver.dto.participation.ParticipationRequestDto;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;

import java.util.List;

public interface EventService {
    List<EventShortDto> getAll(EventRequestParam params, EndpointHitDtoReq endpointHit);

    EventDtoWithComments get(Long eventId, EndpointHitDtoReq endpointHit);

    List<EventShortDto> getUserEvents(Long userId, int from, int size);

    EventDtoWithComments getUserEvent(Long userId, Long eventId);

    EventFullDto create(Long userId, NewEventDto newEvent);

    EventFullDto update(Long userId, Long eventId, UpdateEvent updateEvent);

    List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateUserEventRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest);

    List<EventFullDto> getAllForAdmin(EventRequestParam params);

    EventFullDto updateForAdmin(Long eventId, UpdateEvent updateEvent);

    List<ParticipationRequestDto> getAllParticipation(Long userId);

    ParticipationRequestDto createParticipation(Long userId, Long eventId);

    ParticipationRequestDto cancelParticipation(Long userId, Long participationId);

    CommentDto createComment(Long userId, Long eventId, NewOrUpdateCommentDto newComment);

    CommentDto updateComment(Long userId, Long commentId, NewOrUpdateCommentDto updateComment);

    void deleteComment(Long userId, Long commentId);
}
