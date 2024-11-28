package ru.practicum.mainserver.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.mainserver.service.enums.Role;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 250)
    private String name;
    @Column(name = "email", nullable = false, length = 254, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 32)
    private Role role;
}
