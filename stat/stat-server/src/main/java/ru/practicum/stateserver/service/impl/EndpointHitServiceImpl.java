package ru.practicum.stateserver.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;
import ru.practicum.stateserver.repository.EndpointHitRepository;
import ru.practicum.stateserver.repository.model.NativeEndpointHit;
import ru.practicum.stateserver.repository.model.ShortEndpointHit;
import ru.practicum.stateserver.service.EndpointHitService;
import ru.practicum.stateserver.service.entity.EndpointHit;
import ru.practicum.stateserver.service.mapper.EndpointHitMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EndpointHitServiceImpl implements EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;
    private final EndpointHitMapper endpointHitMapper;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<ViewStats> getStats(String start, String end) {
        log.info("Server stat (service): Try getStats()");

        List<ShortEndpointHit> endpointHits = endpointHitRepository.findAllByCreatedAtBetween(
                toTimestamp(start),
                toTimestamp(end)
        );

        log.info("Server stat (service): Finished getStats()");
        return endpointHitMapper.toDtoList(endpointHits);
    }

    @Override
    public List<ViewStats> getStatsByUris(String start, String end, List<String> uris) {
        log.info("Server stat (service): Try getStatsByUris()");
        List<ShortEndpointHit> endpointHits = endpointHitRepository.findAllByCreatedAtBetweenAndUriInIgnoreCase(
                        toTimestamp(start),
                        toTimestamp(end),
                        uris
        );

        log.info("Server stat (service): Finished getStatsByUris()");
        return endpointHitMapper.toDtoList(endpointHits);
    }

    @Override
    public List<ViewStats> getStatsUnique(String start, String end) {
        log.info("Server stat (service): Try getStatsUnique()");
        List<NativeEndpointHit> endpointHits = endpointHitRepository.findAllByCreatedAtBetweenAndUniqueIp(
                toTimestamp(start),
                toTimestamp(end)
        );

        log.info("Server stat (service): Finished getStatsUnique()");
        return endpointHitMapper.toDtoListNative(endpointHits);
    }

    @Override
    public List<ViewStats> getStatsUniqueByUris(String start, String end, List<String> uris) {
        log.info("Server stat (service): Try getStatsUniqueByUris()");
        List<NativeEndpointHit> endpointHits = endpointHitRepository.findAllByCreatedAtBetweenAndUriInAndUniqueIp(
                toTimestamp(start),
                toTimestamp(end),
                uris
        );

        log.info("Server stat (service): Finished getStatsUniqueByUris()");
        return endpointHitMapper.toDtoListNative(endpointHits);
    }

    @Override
    @Transactional
    public ViewStats createHits(EndpointHitDtoReq request) {
        log.info("Server stat (service): Try createHits()");

        EndpointHit endpointHit = endpointHitMapper.toEntity(request);
        endpointHit = endpointHitRepository.save(endpointHit);

        log.info("Server stat (service): Finished createHits()");
        return endpointHitMapper.toDto(endpointHit);
    }

    private Timestamp toTimestamp(String dateTime) throws DateTimeParseException {
            return Timestamp.valueOf(LocalDateTime.parse(URLDecoder.decode(dateTime, StandardCharsets.UTF_8), dtf));
    }
}
