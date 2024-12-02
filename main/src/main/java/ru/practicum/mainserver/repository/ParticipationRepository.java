package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainserver.service.entity.Participation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findAllByRequester_Id(Long userId);

    List<Participation> findAllByEvent_Id(Long eventId);

    Optional<Participation> findByRequester_IdAndEvent_Id(Long userId, Long eventId);
}
