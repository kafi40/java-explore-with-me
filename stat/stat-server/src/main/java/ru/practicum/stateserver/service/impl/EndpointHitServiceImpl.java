package ru.practicum.stateserver.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.stateserver.repository.EndpointHitRepository;
import ru.practicum.stateserver.service.EndpointHitService;
import ru.practicum.stateserver.service.entity.EndpointHit;
import ru.practicum.stateserver.service.entity.EndpointHitShort;
import ru.practicum.stateserver.service.mapper.EndpointHitMapper;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EndpointHitServiceImpl implements EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;

    @Override
    public List<EndpointHitDtoRes> getStats(String start, String end) {
        log.info("Server stat (service): Try getStats()");

        List<EndpointHit> endpointHits =
                endpointHitRepository.findAllByCreatedAtBetween(Timestamp.valueOf(start), Timestamp.valueOf(end));

        log.info("Server stat (service): Finished getStats()");
        return EndpointHitMapper.toDtoList(endpointHits);
    }

    @Override
    public List<EndpointHitDtoRes> getStatsByUris(String start, String end, List<String> uris) {
        log.info("Server stat (service): Try getStatsByUris()");
        Timestamp fStart = Timestamp.valueOf(start);
        Timestamp fEnd = Timestamp.valueOf(end);
        List<EndpointHit> endpointHits = endpointHitRepository.findAllByCreatedAtBetweenAndUriInIgnoreCase(fStart, fEnd, uris);

        log.info("Server stat (service): Finished getStatsByUris()");
        return EndpointHitMapper.toDtoList(endpointHits);
    }

    @Override
    public List<EndpointHitDtoRes> getStatsUnique(String start, String end) {
        log.info("Server stat (service): Try getStatsUnique()");

        Timestamp fStart = Timestamp.valueOf(start);
        Timestamp fEnd = Timestamp.valueOf(end);
        List<EndpointHit> endpointHits = endpointHitRepository.findAllByCreatedAtBetweenAndUniqueIp(fStart, fEnd);

        log.info("Server stat (service): Finished getStatsUnique()");
        return EndpointHitMapper.toDtoList(endpointHits);
    }

    @Override
    public List<EndpointHitDtoRes> getStatsUniqueByUris(String start, String end, List<String> uris) {
        log.info("Server stat (service): Try getStatsUniqueByUris()");

        Timestamp fStart = Timestamp.valueOf(start);
        Timestamp fEnd = Timestamp.valueOf(end);
        List<EndpointHit> endpointHits = endpointHitRepository.findAllByCreatedAtBetweenAndUriInAndUniqueIp(fStart, fEnd, uris);

        log.info("Server stat (service): Finished getStatsUniqueByUris()");
        return EndpointHitMapper.toDtoList(endpointHits);
    }

    @Override
    @Transactional
    public EndpointHitDtoRes createHits(EndpointHitDtoReq request) {
        log.info("Server stat (service): Try createHits()");

        EndpointHit endpointHit = EndpointHitMapper.fromDto(request);
        endpointHit = endpointHitRepository.save(endpointHit);

        log.info("Server stat (service): Finished createHits()");
        return EndpointHitMapper.toDto(endpointHit);
    }
}
