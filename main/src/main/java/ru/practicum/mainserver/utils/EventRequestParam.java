package ru.practicum.mainserver.utils;


import ru.practicum.mainserver.enums.State;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class EventRequestParam {
    private String text;
    private Set<Long> categories;
    private Boolean paid;
    private String rangeStart;
    private String rangeEnd;
    private boolean onlyAvailable;
    private String sort;
    private int from;
    private int size;
    private List<Long> users;
    private List<State> states;
    private boolean isAdmin;
}
