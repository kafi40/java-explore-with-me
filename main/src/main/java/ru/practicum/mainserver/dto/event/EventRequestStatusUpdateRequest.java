package ru.practicum.mainserver.dto.event;

import ru.practicum.mainserver.enums.Status;
import lombok.Data;

import java.util.Set;

@Data
public class EventRequestStatusUpdateRequest {
    private Set<Long> requestIds;
    private Status status;
}
