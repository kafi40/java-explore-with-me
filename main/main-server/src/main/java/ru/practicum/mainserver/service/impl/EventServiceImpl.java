package ru.practicum.mainserver.service.impl;

import com.example.maincommon.dto.EventRequestParam;
import com.example.maincommon.dto.event.*;
import com.example.maincommon.dto.participation.ParticipationRequestDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.exception.NotFountException;
import ru.practicum.mainserver.repository.EventRepository;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.mainserver.service.entity.Event;
import ru.practicum.mainserver.service.entity.QEvent;
import ru.practicum.mainserver.service.mapper.EventMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getAll(EventRequestParam params) {
        log.info("Server main (EventService): Try getAll()");
        PageRequest page = PageRequest.of(
                params.getFrom() > 0 ? params.getFrom() / params.getSize() : 0, params.getSize()
        );
        BooleanExpression isPaid = QEvent.event.paid;
        BooleanExpression byAnyCat = QEvent.event.category.id.in(params.getCategories());
        BooleanExpression byText = QEvent.event.annotation.like(params.getText())
                .and(QEvent.event.description.like(params.getText()));

        return eventRepository.findMany(page).stream()
                .sorted()
                .map(eventMapper::toShortDto)
                .toList();
    }

    @Override
    public EventFullDto get(Long eventId) {
        log.info("Server main (EventService): Try get()");
        return eventRepository.findById(eventId)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new NotFountException("Server main (EventService): Not found event with id: " + eventId));
    }

    @Override
    public List<EventShortDto> getUserEvents(Long userId, int from, int size) {
        log.info("Server main (EventService): Try getUserEvents()");
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return eventRepository.findMany(page).stream()
                .map(eventMapper::toShortDto)
                .toList();
    }

    @Override
    public EventFullDto getUserEvent(Long userId, Long eventId) {
        log.info("Server main (EventService): Try getUserEvent()");
        return eventRepository.findById(eventId)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new NotFountException("Server main (EventService): Not found event with id: " + eventId));
    }

    @Override
    public EventFullDto create(Long userId, NewEventDto newEvent) {
        log.info("Server main (EventService): Try create()");
        Event event = eventMapper.toEntity(newEvent);
        event =eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    public EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest updateEvent) {
        log.info("Server main (EventService): Try update()");
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFountException("Server main (EventService): Not found event with id: " + eventId));
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId) {
        log.info("Server main (EventService): Try getUserEventRequests()");
        return List.of();
    }

    @Override
    public List<EventRequestStatusUpdateResult> updateUserEventRequests(
            Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest) {
        log.info("Server main (EventService): Try updateUserEventRequests()");
        return List.of();
    }

    @Override
    public List<EventFullDto> getAllForAdmin(EventRequestParam params) {
        log.info("Server main (EventService): Try getAllForAdmin()");
        PageRequest page = PageRequest.of(params.getFrom() > 0 ? params.getFrom() / params.getSize() : 0, params.getSize());
        return eventRepository.findMany(page).stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    public EventFullDto updateForAdmin(Long eventId, UpdateEventUserRequest updateEvent) {
        log.info("Server main (EventService): Try updateForAdmin()");
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFountException("Server main (EventService): Not found event with id: " + eventId));

        return null;
    }
}
