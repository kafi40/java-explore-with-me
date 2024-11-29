package ru.practicum.mainserver.controller;

import com.example.maincommon.dto.EventRequestParam;
import com.example.maincommon.dto.State;
import com.example.maincommon.dto.event.*;
import com.example.maincommon.dto.participation.ParticipationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.EventService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    @GetMapping("/events")
    public List<EventShortDto> getAll(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false, defaultValue = "false") boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "false") boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "EVENT_DATE") String sort,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size

    ) {
        log.info("Server main (controller): Get events with param text={}, categories={}, paid={}, rangeStart={}, " +
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
                .build();
        return eventService.getAll(requestParam);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto get(@PathVariable Long eventId) {
        log.info("Server main (controller): Get events with param eventId={}", eventId);
        return eventService.get(eventId);
    }

    @GetMapping("users/{userId}/events")
    public List<EventShortDto> getUserEvents(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size
            ) {
        log.info("Server main (controller): Get user's events with userId={}, from={}, size={}", userId, from, size);
        return eventService.getUserEvents(userId, from, size);
    }


    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getUserEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Server main (controller): Get user's event with userId={}, eventId={}", userId, eventId);
        return eventService.getUserEvent(userId, eventId);
    }

    @PostMapping("users/{userId}/events")
    public EventFullDto create(@PathVariable Long userId, @RequestBody NewEventDto newEvent) {
        log.info("Server main (controller): Create event {} by user={}", newEvent, userId);
        return eventService.create(userId, newEvent);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto update(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody UpdateEventUserRequest updateEvent
    ) {
        log.info("Server main (controller): Update user's event with userId={}, eventId={}, body={} ", userId, eventId, updateEvent);
        return eventService.update(userId, eventId, updateEvent);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getUserEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Server main (controller): Get requests for user's event with userId={}, eventId={}", userId, eventId);
        return eventService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public List<EventRequestStatusUpdateResult> updateUserEventRequests(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody EventRequestStatusUpdateRequest request
            ) {
        log.info("Server main (controller): Update request for user's event with userId={}, eventId={}, body={} ", userId, eventId, request);
        return eventService.updateUserEventRequests(userId, eventId, request);
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getAllForAdmin(
            @RequestParam(required = false) List<Integer> users,
            @RequestParam(required = false) List<State> states,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size

    ) {
        log.info("Server main (controller): Get events with param users={}, states={}, categories={}, rangeStart={}, " +
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
                .build();
        return eventService.getAllForAdmin(requestParam);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateForAdmin(@PathVariable Long eventId, @RequestBody UpdateEventUserRequest updateEvent) {
        log.info("Server main (controller): Update eventId={} with body {}", eventId, updateEvent);
        return eventService.updateForAdmin(eventId, updateEvent);
    }
}
