package ru.practicum.mainserver.service.entity;

import lombok.NoArgsConstructor;
import ru.practicum.mainserver.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "participations")
@NoArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created", nullable = false)
    private Timestamp created;
    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;
    @JoinColumn(name = "requester_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User requester;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
}
