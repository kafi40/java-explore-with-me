package ru.practicum.mainserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainserver.service.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
