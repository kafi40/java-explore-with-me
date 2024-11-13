package ru.practicum.statcommon.dto;

import lombok.Data;

@Data
public class EndpointHitDtoReq {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
