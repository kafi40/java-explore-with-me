package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainserver.service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
