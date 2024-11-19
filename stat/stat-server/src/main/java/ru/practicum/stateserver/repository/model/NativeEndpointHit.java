package ru.practicum.stateserver.repository.model;

public interface NativeEndpointHit {
    String getApp();

    String getUri();

    Long getCount();
}
