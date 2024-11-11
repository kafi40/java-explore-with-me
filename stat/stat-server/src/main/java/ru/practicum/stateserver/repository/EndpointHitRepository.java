package ru.practicum.stateserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stateserver.service.entity.EndpointHit;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    List<EndpointHit> findAllByCreatedAtBetween(Timestamp start, Timestamp end);

    List<EndpointHit> findAllByCreatedAtBetweenAndUriInIgnoreCase(Timestamp start, Timestamp end, List<String> uris);

    @Query("SELECT DISTINCT e.uri, e.ip FROM EndpointHit AS e " +
            "WHERE (e.createdAt BETWEEN ?1 AND ?2)")
    List<EndpointHit> findAllByCreatedAtBetweenAndUniqueIp(Timestamp start, Timestamp end);

    @Query("SELECT DISTINCT e.uri, e.ip FROM EndpointHit AS e " +
            "WHERE (e.createdAt BETWEEN ?1 AND ?2) " +
            "AND e.uri IN ?3 ")
    List<EndpointHit> findAllByCreatedAtBetweenAndUriInAndUniqueIp(Timestamp start, Timestamp end, List<String> uris);
}
