package ru.practicum.mainserver.exception;

public class TimeTooEarlyException extends RuntimeException {
    public TimeTooEarlyException(String message) {
        super(message);
    }
}
