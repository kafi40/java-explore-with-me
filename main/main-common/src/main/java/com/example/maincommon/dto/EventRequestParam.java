package com.example.maincommon.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EventRequestParam {
    private String text;
    private List<Integer> categories;
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
