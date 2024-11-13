package ru.practicum.stateserver.factory;

import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.stateserver.util.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModelFactory {
    private static final String MAIN_SERVER = "main-server";

    public static EndpointHitDtoRes createEndpointHitDtoRes() {
        return new EndpointHitDtoRes(MAIN_SERVER, "http://localhost:9090/stats", 1);
    }

    public static EndpointHitDtoReq createEndpointHitDtoReq() {
        EndpointHitDtoReq req = new EndpointHitDtoReq();
        req.setApp(MAIN_SERVER);
        req.setUri("http://localhost:9090/stats");
        req.setIp("192.168.1.1");
        req.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return req;
    }
}
