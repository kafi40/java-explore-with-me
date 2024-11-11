package ru.practicum.stateserver.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortEndpointHit implements NativeEndpointHit {
    private String app;
    private String uri;
    private Long count;
}
