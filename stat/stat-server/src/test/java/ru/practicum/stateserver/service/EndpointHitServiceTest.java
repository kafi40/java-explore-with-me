package ru.practicum.stateserver.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;
import ru.practicum.stateserver.factory.ModelFactory;
import ru.practicum.stateserver.repository.model.ShortEndpointHit;
import ru.practicum.stateserver.util.Util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class EndpointHitServiceTest {
    private final EndpointHitService endpointHitService;
    private final EntityManager em;
    private final Timestamp startTS = Timestamp.valueOf(LocalDateTime.now().minusDays(2));
    private final Timestamp endTS = Timestamp.valueOf(LocalDateTime.now().plusDays(2));
    private final String start = Util.toString(startTS);
    private final String end =  Util.toString(endTS);
    private EndpointHitDtoReq request;
    private List<ViewStats> response;

    @BeforeEach
    void beforeEach() {
        request = ModelFactory.createEndpointHitDtoReq();
        endpointHitService.createHits(request);
    }

    @Test
    void testGetStats() {
        response = endpointHitService.getStats(start, end);
        TypedQuery<ShortEndpointHit> query = em.createQuery(
                "SELECT new ru.practicum.stateserver.repository.model.ShortEndpointHit(e.app, e.uri, count(e.uri)) " +
                        "FROM EndpointHit AS e " +
                        "WHERE e.createdAt BETWEEN :start AND :end " +
                        "GROUP BY e.uri, e.app " +
                        "ORDER BY COUNT(e.uri) DESC", ShortEndpointHit.class);

        List<ShortEndpointHit> endpointHits = query
                .setParameter("start", startTS)
                .setParameter("end", endTS)
                .getResultList();
        assertThat(response.size(), equalTo(endpointHits.size()));
        assertThat(response.getFirst().app(), equalTo(endpointHits.getFirst().getApp()));
        assertThat(response.getFirst().uri(), equalTo(endpointHits.getFirst().getUri()));
        assertThat(response.getFirst().hits(), equalTo(1));
    }

    @Test
    void testGetStatsByUris() {
        String awaitingUri = request.getUri();
        request.setUri("http://localhost:9090/otherUri");
        endpointHitService.createHits(request);

        response = endpointHitService.getStatsByUris(start, end, List.of(awaitingUri));

        TypedQuery<ShortEndpointHit> query = em.createQuery(
                "SELECT new ru.practicum.stateserver.repository.model.ShortEndpointHit(e.app, e.uri, count(e.uri)) " +
                        "FROM EndpointHit AS e " +
                        "WHERE e.createdAt BETWEEN :start AND :end " +
                        "AND e.uri IN :uris " +
                        "GROUP BY e.uri, e.app " +
                        "ORDER BY COUNT(e.uri) DESC", ShortEndpointHit.class);

        List<ShortEndpointHit> endpointHits = query
                .setParameter("start", startTS)
                .setParameter("end", endTS)
                .setParameter("uris", awaitingUri)
                .getResultList();
        assertThat(response.size(), equalTo(endpointHits.size()));
        assertThat(response.getFirst().app(), equalTo(endpointHits.getFirst().getApp()));
        assertThat(response.getFirst().uri(), equalTo(awaitingUri));
        assertThat(response.getFirst().hits(), equalTo(1));
    }

    @Test
    void testGetStatsUnique() {
        endpointHitService.createHits(request);

        response = endpointHitService.getStatsUnique(start, end);

        assertThat(response.getFirst().hits(), equalTo(1));
    }

    @Test
    void testGetStatsUniqueByUris() {
        request.setUri("http://localhost:9090/otherUri");
        endpointHitService.createHits(request);
        response = endpointHitService.getStatsUniqueByUris(start, end, List.of(request.getUri()));

        assertThat(response.getFirst().hits(), equalTo(1));
        assertThat(response.getFirst().uri(), equalTo(request.getUri()));
        assertThat(response.size(), equalTo(1));
    }
}
