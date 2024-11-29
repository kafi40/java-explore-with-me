package com.example.maincommon.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class EventRequestParam {
    private String text;
    private Set<Long> categories;
    private boolean paid;
    private String rangeStart;
    private String rangeEnd;
    private boolean onlyAvailable;
    private String sort;
    private int from;
    private int size;
    private List<Integer> users;
    private List<State> states;
}
