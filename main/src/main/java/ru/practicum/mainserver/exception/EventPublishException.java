package ru.practicum.mainserver.exception;

public class EventPublishException extends RuntimeException {
    public EventPublishException(String message) {
        super(message);
    }
}
