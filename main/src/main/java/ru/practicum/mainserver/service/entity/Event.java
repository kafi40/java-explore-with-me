package ru.practicum.mainserver.service.entity;

import lombok.NoArgsConstructor;
import ru.practicum.mainserver.enums.State;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "events")
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false, length = 120)
    private String title;
    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;
    @Column(name = "description", nullable = false, length = 7000)
    private String description;
    @Column(name = "event_date", nullable = false)
    private Timestamp eventDate;
    @Column(name = "created_on", nullable = false)
    private Timestamp createdOn;
    @Column(name = "published_on")
    private Timestamp publishedOn;
    @Column(name = "paid", nullable = false)
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "confirmed_requests", nullable = false)
    private Integer confirmedRequests;
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;
    @JoinColumn(name = "initiator_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User initiator;
    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @JoinColumn(name = "location_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;
    @Column(name = "views")
    private Integer views;
}
