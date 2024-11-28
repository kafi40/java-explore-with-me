package ru.practicum.stateserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stateserver.repository.model.NativeEndpointHit;
import ru.practicum.stateserver.repository.model.ShortEndpointHit;
import ru.practicum.stateserver.service.entity.EndpointHit;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.stateserver.repository.model.ShortEndpointHit(e.app, e.uri, count(e.uri))" +
            "FROM EndpointHit AS e " +
            "WHERE e.createdAt BETWEEN ?1 AND ?2 " +
            "GROUP BY e.uri, e.app " +
            "ORDER BY COUNT(e.uri) DESC")
    List<ShortEndpointHit> findAllByCreatedAtBetween(Timestamp start, Timestamp end);

    @Query("SELECT new ru.practicum.stateserver.repository.model.ShortEndpointHit(e.app, e.uri, count(e.uri)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.createdAt BETWEEN ?1 AND ?2 " +
            "AND e.uri IN ?3 " +
            "GROUP BY e.uri, e.app " +
            "ORDER BY COUNT(e.uri) DESC")
    List<ShortEndpointHit> findAllByCreatedAtBetweenAndUriInIgnoreCase(Timestamp start, Timestamp end, List<String> uris);

    @Query(value = "SELECT e.uri, e.app, COUNT(e.ip) as count " +
            "FROM (SELECT DISTINCT ei.uri, ei.ip, ei.app " +
                "FROM endpoint_hits AS ei " +
                "WHERE ei.created_at BETWEEN ?1 AND ?2) AS e " +
            "GROUP BY e.uri,  e.ip, e.app " +
            "ORDER BY count DESC", nativeQuery = true)
    List<NativeEndpointHit> findAllByCreatedAtBetweenAndUniqueIp(Timestamp start, Timestamp end);

    @Query(value = "SELECT e.uri, e.app, COUNT(e.ip) as count " +
            "FROM (SELECT DISTINCT ei.uri, ei.ip, ei.app " +
                "FROM endpoint_hits AS ei " +
                "WHERE ei.created_at BETWEEN ?1 AND ?2 AND ei.uri IN ?3) AS e " +
            "GROUP BY e.uri,  e.ip, e.app " +
            "ORDER BY count DESC", nativeQuery = true)
    List<NativeEndpointHit> findAllByCreatedAtBetweenAndUriInAndUniqueIp(Timestamp start, Timestamp end, List<String> uris);
}
