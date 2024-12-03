package ru.practicum.mainserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.mainserver.utils.EventRequestParam;
import ru.practicum.mainserver.enums.State;
import ru.practicum.mainserver.annotation.PositiveList;
import ru.practicum.mainserver.dto.event.*;
import ru.practicum.mainserver.dto.participation.ParticipationRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventController {
    private final EventService eventService;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/events")
    public List<EventShortDto> getAll(
            @RequestParam(required = false) String text,
            @PositiveList @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "false") boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "EVENT_DATE") String sort,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size,
            HttpServletRequest servletRequest
    ) {
        log.info("Server main (EventController): Get events with param text={}, categories={}, paid={}, rangeStart={}, " +
                        "rangeEnd={}, onlyAvailable={}, sort={}, from={}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        EventRequestParam requestParam = EventRequestParam.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .isAdmin(false)
                .build();
        EndpointHitDtoReq endpointHit = EndpointHitDtoReq.builder()
                .app("main-service")
                .uri(servletRequest.getRequestURI())
                .ip(servletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(dtf))
                .build();
        return eventService.getAll(requestParam, endpointHit);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto get(@PositiveOrZero @PathVariable Long eventId, HttpServletRequest servletRequest) {
        log.info("Server main (EventController): Get events with param eventId={}", eventId);
        EndpointHitDtoReq endpointHit = EndpointHitDtoReq.builder()
                .app("main-service")
                .uri(servletRequest.getRequestURI())
                .ip(servletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(dtf))
                .build();
        return eventService.get(eventId, endpointHit);
    }

    @GetMapping("users/{userId}/events")
    public List<EventShortDto> getUserEvents(
            @Positive @PathVariable Long userId,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size
            ) {
        log.info("Server main (EventController): Get user's events with userId={}, from={}, size={}", userId, from, size);
        return eventService.getUserEvents(userId, from, size);
    }


    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getUserEvent(@Positive @PathVariable Long userId, @Positive @PathVariable Long eventId) {
        log.info("Server main (EventController): Get user's event with userId={}, eventId={}", userId, eventId);
        return eventService.getUserEvent(userId, eventId);
    }

    @PostMapping("users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@Positive @PathVariable Long userId, @Valid @RequestBody NewEventDto newEvent) {
        log.info("Server main (EventController): Create event {} by user={}", newEvent, userId);
        return eventService.create(userId, newEvent);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto update(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody UpdateEvent updateEvent
    ) {
        log.info("Server main (EventController): Update user's event with userId={}, eventId={}, body={} ", userId, eventId, updateEvent);
        return eventService.update(userId, eventId, updateEvent);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getUserEventRequests(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId) {
        log.info("Server main (EventController): Get requests for user's event with userId={}, eventId={}", userId, eventId);
        return eventService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateUserEventRequests(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest request
            ) {
        log.info("Server main (EventController): Update request for user's event with userId={}, eventId={}, body={} ", userId, eventId, request);
        return eventService.updateUserEventRequests(userId, eventId, request);
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getAllForAdmin(
            @PositiveList @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<State> states,
            @PositiveList @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size

    ) {
        log.info("Server main (EventController): Get events with param users={}, states={}, categories={}, rangeStart={}, " +
                        "rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        EventRequestParam requestParam = EventRequestParam.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .from(from)
                .size(size)
                .isAdmin(true)
                .build();
        return eventService.getAllForAdmin(requestParam);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateForAdmin(
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody UpdateEvent updateEvent) {
        log.info("Server main (EventController): Update eventId={} with body {}", eventId, updateEvent);
        return eventService.updateForAdmin(eventId, updateEvent);
    }

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getAllParticipation(@Positive @PathVariable Long userId) {
        log.info("Server main (EventController): Get participation for user {}", userId);
        return eventService.getAllParticipation(userId);
    }

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createParticipation(
            @Positive @PathVariable Long userId,
            @Positive @RequestParam Long eventId
    ) {
        log.info("Server main (EventController): Create participation with user={}, event={}", userId, eventId);
        return eventService.createParticipation(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestsId}/cancel")
    public ParticipationRequestDto cancelParticipation(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long requestsId
    ) {
        log.info("Server main (EventController): Cancel participation with ID={} for user with ID={}", requestsId, userId);
        return eventService.cancelParticipation(userId, requestsId);
    }
}
