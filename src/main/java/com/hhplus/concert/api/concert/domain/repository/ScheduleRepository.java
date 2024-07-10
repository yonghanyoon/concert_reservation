package com.hhplus.concert.api.concert.domain.repository;

import com.hhplus.concert.api.concert.domain.entity.Schedule;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    List<Schedule> findByConcertIdAndScheduleDateAfter(Long concertId, LocalDateTime now);
}
