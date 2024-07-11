package com.hhplus.concert.api.concert.domain.repository;

import com.hhplus.concert.api.concert.domain.entity.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByConcertIdAndScheduleDateAfter(Long concertId, LocalDateTime now);
}
