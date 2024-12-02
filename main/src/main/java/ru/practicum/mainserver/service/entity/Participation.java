package ru.practicum.mainserver.service.entity;

import ru.practicum.mainserver.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "participations")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created", nullable = false)
    private Timestamp created;
    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne
    private Event event;
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User requester;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
}
