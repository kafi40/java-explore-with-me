package ru.practicum.stateserver.service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "endpoint_hit")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "app", nullable = false, length = 50)
    private String app;
    @Column(name = "uri", nullable = false)
    private String uri;
    @Column(name = "ip", nullable = false, length = 20)
    private String ip;
    @Column(name = "created_at")
    private Timestamp createdAt;

}
