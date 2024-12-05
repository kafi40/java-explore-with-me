package ru.practicum.mainserver.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "text", nullable = false, length = 1000)
    private String text;
    @Column(name = "created_on", nullable = false)
    private Timestamp createdOn;
    @Column(name = "is_modified", nullable = false)
    private Boolean isModified;
    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne
    private Event event;
    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne
    private User author;
}
