package ru.practicum.mainserver.repository.model;

import ru.practicum.mainserver.enums.State;
import ru.practicum.mainserver.service.entity.Category;
import ru.practicum.mainserver.service.entity.Location;
import ru.practicum.mainserver.service.entity.User;

import java.sql.Timestamp;

public interface EventNative {
    Long getId();
    String getTitle();
    String getAnnotation();
    String getDescription();
    Timestamp getEventDate();
    Timestamp getCreatedOn();
    Timestamp getPublishedOn();
    Boolean getPaid();
    Integer getParticipantLimit();
    Boolean getRequestModeration();
    State getState();
    User getInitiator();
    Category getCategory();
    Location getLocation();
    Integer getConfirmedRequests();
}
