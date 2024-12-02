package ru.practicum.mainserver.repository.model;

import lombok.Data;

@Data
public class ParticipationCountInfo {
    private Long id;
    private Integer limit;
    private Long count;

    public ParticipationCountInfo(Long id, Long count) {
        this.id = id;
        this.count = count;
    }

    public ParticipationCountInfo(Integer limit, Long count) {
        this.limit = limit;
        this.count = count;
    }
}
