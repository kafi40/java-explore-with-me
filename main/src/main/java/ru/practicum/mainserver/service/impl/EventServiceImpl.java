package ru.practicum.mainserver.service.impl;

import org.springframework.data.querydsl.QSort;
import ru.practicum.mainserver.utils.EventRequestParam;
import ru.practicum.mainserver.enums.State;
import ru.practicum.mainserver.enums.StateAction;
import ru.practicum.mainserver.enums.Status;
import ru.practicum.mainserver.dto.event.*;
import ru.practicum.mainserver.dto.participation.ParticipationRequestDto;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.exception.*;
import ru.practicum.mainserver.repository.*;
import ru.practicum.mainserver.repository.ParticipationRepository;
import ru.practicum.mainserver.service.EventService;
import ru.practicum.mainserver.service.entity.*;
import ru.practicum.mainserver.service.mapper.EventMapper;
import ru.practicum.mainserver.service.mapper.LocationMapper;
import ru.practicum.mainserver.service.mapper.ParticipationMapper;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;
import ru.practicum.statgateway.client.EndpointHitClient;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;
    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;
    private final ParticipationMapper participationMapper;
    private final EndpointHitClient endpointHitClient;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getAll(EventRequestParam params, EndpointHitDtoReq endpointHit) {
        log.info("Server main (EventService): Try getAll()");
        PageRequest page;
        if (params.getSort().equals("VIEWS")) {
            page = PageRequest.of(params.getFrom(), params.getSize(), new QSort(QEvent.event.views.desc()));
        } else {
            page = PageRequest.of(params.getFrom(), params.getSize(), new QSort(QEvent.event.eventDate.desc()));
        }
        BooleanBuilder queryParams = getSQLQuery(params);
        List<Event> events = eventRepository.findAll(queryParams, page).getContent();
        for (Event event : events) {
            endpointHit.setUri(endpointHit.getUri() + "/" + event.getId());
            endpointHitClient.create(endpointHit);
        }
        return events.stream()
                .map(eventMapper::toShortDto)
                .toList();
    }

    @Override
    public EventFullDto get(Long eventId, EndpointHitDtoReq endpointHit) {
        log.info("Server main (EventService): Try get()");
        return eventRepository.findById(eventId)
                .map(event -> {
                    if (event.getState().equals(State.PUBLISHED)) {
                            endpointHitClient.create(endpointHit);
                            Map<String, String> reqParam = new HashMap<>();
                            reqParam.put("start", fromTimestamp(event.getCreatedOn()));
                            reqParam.put("end", fromTimestamp(event.getEventDate()));
                            reqParam.put("unique", "true");
                            List<ViewStats> stats = endpointHitClient.get(reqParam, List.of(endpointHit.getUri()));
                            event.setViews(stats == null ? event.getViews() : stats.getFirst().hits());
                            event = eventRepository.save(event);
                        return eventMapper.toDto(event);
                    } else {
                        throw new NotFountException("Server main (EventService): Not found event with id: " + eventId);
                    }
                })
                .orElseThrow(() -> new NotFountException("Server main (EventService): Not found event with id: " + eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getUserEvents(Long userId, int from, int size) {
        log.info("Server main (EventService): Try getUserEvents()");
        PageRequest page = PageRequest.of(from, size);
        return eventRepository.findAll(page).stream()
                .map(eventMapper::toShortDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getUserEvent(Long userId, Long eventId) {
        log.info("Server main (EventService): Try getUserEvent()");
        return eventRepository.findById(eventId)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new NotFountException("Server main (EventService): Not found event with id: " + eventId));
    }

    @Override
    public EventFullDto create(Long userId, NewEventDto newEvent) {
        log.info("Server main (EventService): Try create()");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFountException(
                        "Server main (EventService): Not found user with id: " + userId));
        Category category = categoryRepository.findById(newEvent.getCategory())
                .orElseThrow(() -> new NotFountException(
                        "Server main (EventService): Not found category with id: " + newEvent.getCategory()));
        Location location = locationMapper.toEntity(newEvent.getLocation());
        Event event = eventMapper.toEntity(newEvent);
        event.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        event.setInitiator(user);
        event.setCategory(category);
        event.setState(State.PENDING);
        event.setLocation(location);
        event.setEventDate(eventTimeValidator(newEvent.getEventDate()));
        event.setPaid(newEvent.getPaid() != null ? newEvent.getPaid() : false);
        event.setParticipantLimit(newEvent.getParticipantLimit() != null ? newEvent.getParticipantLimit() : 0);
        event.setRequestModeration(newEvent.getRequestModeration() != null ? newEvent.getRequestModeration() : true);
        event.setPaid(newEvent.getPaid() != null ? newEvent.getPaid() : false);
        event.setConfirmedRequests(0);
        event.setViews(0);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    public EventFullDto update(Long userId, Long eventId, UpdateEvent updateEvent) {
        log.info("Server main (EventService): Try update()");
        Event event = getUpdate(eventId, updateEvent, false);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId) {
        log.info("Server main (EventService): Try getUserEventRequests()");
        return participationRepository.findAllByEvent_Id(eventId).stream()
                .map(participationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getAllForAdmin(EventRequestParam params) {
        log.info("Server main (EventService): Try getAllForAdmin()");
        PageRequest page = PageRequest.of(params.getFrom(), params.getSize());
        BooleanBuilder queryParams = getSQLQuery(params);
        List<Event> events = eventRepository.findAll(queryParams, page).getContent();
        return events.stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    public EventFullDto updateForAdmin(Long eventId, UpdateEvent updateEvent) {
        log.info("Server main (EventService): Try updateForAdmin()");
        Event event = getUpdate(eventId, updateEvent, true);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getAllParticipation(Long userId) {
        log.info("Server main (EventService): Try getAllParticipation()");
        return participationRepository.findAllByRequester_Id(userId).stream()
                .map(participationMapper::toDto)
                .toList();
    }

    @Override
    public ParticipationRequestDto createParticipation(Long userId, Long eventId) {
        log.info("Server main (EventService): Try createParticipation()");
        participationRepository.findByRequester_IdAndEvent_Id(userId, eventId)
                .ifPresent(value -> {
                    throw new ParticipationRegisterException("Server main (EventService): Вы уже зарегистрированы");
                });
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFountException(
                        "Server main (EventService): Not found event with id: " + eventId));
        if (event.getConfirmedRequests() >= event.getParticipantLimit() && event.getParticipantLimit() != 0)
            throw new ParticipationRegisterException("Server main (EventService): Достигнут лимит запросов на участие");
        if (event.getInitiator().getId().equals(userId))
            throw new ParticipationRegisterException("Server main (EventService): Нельзя зарегистрироваться в своем событии");
        if (!event.getState().equals(State.PUBLISHED))
            throw new ParticipationRegisterException("Server main (EventService): Нельзя зарегистрироваться в не опубликованном событии");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFountException(
                        "Server main (EventService): Not found user with id: " + userId));
        Participation participation = new Participation();
        participation.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        participation.setEvent(event);
        participation.setRequester(user);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            participation.setStatus(Status.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        } else {
            participation.setStatus(Status.PENDING);
        }
        participation = participationRepository.save(participation);
        return participationMapper.toDto(participation);
    }

    @Override
    public EventRequestStatusUpdateResult updateUserEventRequests(
            Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest) {
        log.info("Server main (EventService): Try updateUserEventRequests()");
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFountException(
                        "Server main (EventService): Not found event with id: " + eventId));
        int limit = event.getParticipantLimit();
        int currentCount = event.getConfirmedRequests();
        if (limit != 0 && limit <= currentCount)
            throw new ParticipationRegisterException("Server main (EventService): Превышен лимит заявок");
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        List<Participation> participation = participationRepository.findAllById(updateRequest.getRequestIds());
        for (Participation p : participation) {
            if (!p.getStatus().equals(Status.PENDING))
                throw new ParticipationRegisterException("Server main (EventService): Невозможно изменить запрос");

            if (updateRequest.getStatus().equals(Status.CONFIRMED) && limit > currentCount) {
                p.setStatus(updateRequest.getStatus());
                confirmedRequests.add(participationMapper.toDto(p));
                currentCount++;
            } else if (updateRequest.getStatus().equals(Status.CONFIRMED) && limit == currentCount) {
                p.setStatus(Status.REJECTED);
                rejectedRequests.add(participationMapper.toDto(p));
            } else {
                p.setStatus(Status.REJECTED);
                rejectedRequests.add(participationMapper.toDto(p));
            }
        }
        participationRepository.saveAll(participation);
        event.setConfirmedRequests(currentCount);
        eventRepository.save(event);
        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    @Override
    public ParticipationRequestDto cancelParticipation(Long userId, Long participationId) {
        log.info("Server main (EventService): Try cancelParticipation()");
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new NotFountException(
                        "Server main (EventService): Not found participation with id: " + participationId));
        if (participation.getStatus().equals(Status.CONFIRMED))
            throw new ParticipationRegisterException("Нельзя отменить уже принятую заявку");
        participation.setStatus(Status.CANCELED);
        participation = participationRepository.save(participation);
        return participationMapper.toDto(participation);
    }

    private Event getUpdate(Long eventId, UpdateEvent updateEvent, Boolean isAdmin) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFountException("Server main (EventService): Not found event with id: " + eventId));

        if (!isAdmin && event.getState().equals(State.PUBLISHED))
            throw new EventPublishException("Server main (EventService): Нельзя изменить опубликованное событие");

        if (updateEvent.getStateAction() != null) {
            if (isAdmin) {
                if (updateEvent.getStateAction().equals(StateAction.PUBLISH_EVENT) && event.getState().equals(State.PUBLISHED))
                    throw new EventPublishException("Server main (EventService): Событие уже опубликовано");
                if (updateEvent.getStateAction().equals(StateAction.PUBLISH_EVENT) && event.getState().equals(State.CANCELED))
                    throw new EventPublishException("Server main (EventService): Нельзя опубликовать отмененное событие");
                if (updateEvent.getStateAction().equals(StateAction.REJECT_EVENT) && event.getState().equals(State.PUBLISHED))
                    throw new EventPublishException("Server main (EventService): Нельзя отменить опубликованное событие");
                switch (updateEvent.getStateAction()) {
                    case REJECT_EVENT -> event.setState(State.CANCELED);
                    case PUBLISH_EVENT -> {
                        event.setPublishedOn(Timestamp.valueOf(LocalDateTime.now()));
                        event.setState(State.PUBLISHED);
                    }
                    default -> throw new NotPermissionException("Server main (EventService): Недостаточно прав");
                }
            } else {
                switch (updateEvent.getStateAction()) {
                    case CANCEL_REVIEW -> event.setState(State.CANCELED);
                    case SEND_TO_REVIEW -> event.setState(State.PENDING);
                    default -> throw new NotPermissionException("Server main (EventService): Недостаточно прав");
                }
            }
        }

        if (updateEvent.getAnnotation() != null)
            event.setAnnotation(updateEvent.getAnnotation());

        if (updateEvent.getCategory() != null) {
            Category category = categoryRepository.findById(updateEvent.getCategory())
                    .orElseThrow(() -> new NotFountException(
                            "Server main (EventService): Not found category with id: " + updateEvent.getCategory()));
            event.setCategory(category);
        }

        if (updateEvent.getDescription() != null)
            event.setDescription(updateEvent.getDescription());

        if (updateEvent.getEventDate() != null)
            event.setEventDate(eventTimeValidator(updateEvent.getEventDate()));

        if (updateEvent.getLocation() != null)
            event.setLocation(locationMapper.toEntity(updateEvent.getLocation()));

        if (updateEvent.getPaid() != null)
            event.setPaid(updateEvent.getPaid());

        if (updateEvent.getParticipantLimit() != null)
            event.setParticipantLimit(updateEvent.getParticipantLimit());

        if (updateEvent.getRequestModeration() != null)
            event.setRequestModeration(updateEvent.getRequestModeration());

        if (updateEvent.getTitle() != null)
            event.setTitle(updateEvent.getTitle());
        return event;
    }

    private BooleanBuilder getSQLQuery(EventRequestParam params) {
        QEvent qEvent = QEvent.event;
        BooleanBuilder queryParams = new BooleanBuilder();
        if (params.isAdmin() && params.getStates() != null && !params.getStates().isEmpty())
            queryParams.and(qEvent.state.in(params.getStates()));
        if (!params.isAdmin())
            queryParams.and(qEvent.state.eq(State.PUBLISHED));
        if (params.isAdmin() && params.getUsers() != null && !params.getUsers().isEmpty())
            queryParams.and(qEvent.initiator.id.in(params.getUsers()));
        if (!params.isAdmin() && params.getText() != null && !params.getText().isEmpty())
            queryParams.and(qEvent.annotation.contains(params.getText())).or(qEvent.description.contains(params.getText()));
        if (params.getCategories() != null && !params.getCategories().isEmpty())
            queryParams.and(qEvent.category.id.in(params.getCategories()));
        if (params.getRangeStart() != null)
            queryParams.and(qEvent.eventDate.after(Timestamp.valueOf(params.getRangeStart())));
        if (params.getRangeEnd() != null)
            queryParams.and(qEvent.eventDate.before(Timestamp.valueOf(params.getRangeEnd())));
        if (!params.isAdmin() && params.isOnlyAvailable())
            queryParams.andNot(qEvent.confirmedRequests.eq(QEvent.event.participantLimit));
        if (!params.isAdmin() && params.getPaid() != null)
            queryParams.and(qEvent.paid.eq(params.getPaid()));
        return queryParams;
    }

    private Timestamp eventTimeValidator(String dateTime) {
        Timestamp timestamp = Timestamp.valueOf(
                LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (timestamp.after(Timestamp.valueOf(LocalDateTime.now().plusHours(2)))) {
            return timestamp;
        } else {
            throw new TimeTooEarlyException("Server main (EventService): Время события должно быть не ранее чем за два часа");
        }
    }

    private String fromTimestamp(Timestamp timestamp) {
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

