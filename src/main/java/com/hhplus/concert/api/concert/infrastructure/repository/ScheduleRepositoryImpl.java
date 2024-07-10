package com.hhplus.concert.api.concert.infrastructure.repository;

import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JpaScheduleRepository jpaScheduleRepository;

    @Autowired
    public ScheduleRepositoryImpl(JpaScheduleRepository jpaScheduleRepository) {
        this.jpaScheduleRepository = jpaScheduleRepository;
    }

    @Override
    public List<Schedule> findByConcertIdAndScheduleDateAfter(Long concertId, LocalDateTime now) {
        return jpaScheduleRepository.findByConcertIdAndScheduleDateAfter(concertId, now);
    }
}
