package com.hhplus.concert.api.concert.infrastructure.repository;

import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.domain.repository.SeatRepository;
import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SeatRepositoryImpl implements SeatRepository {

    private final JpaSeatRepository jpaSeatRepository;

    @Autowired
    public SeatRepositoryImpl(JpaSeatRepository jpaSeatRepository) {
        this.jpaSeatRepository = jpaSeatRepository;
    }

    @Override
    public List<Seat> findByScheduleIdAndSeatStatus(Long scheduleId, SeatStatus seatStatus) {
        return jpaSeatRepository.findByScheduleIdAndSeatStatus(scheduleId, seatStatus);
    }
}
