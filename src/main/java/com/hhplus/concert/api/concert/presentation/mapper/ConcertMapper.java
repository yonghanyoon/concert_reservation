package com.hhplus.concert.api.concert.presentation.mapper;

import com.hhplus.concert.api.concert.domain.entity.Concert;
import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.presentation.dto.response.ConcertResDTO;
import com.hhplus.concert.api.concert.presentation.dto.response.ScheduleResDTO;
import com.hhplus.concert.api.concert.presentation.dto.response.SeatResDTO;
import java.util.ArrayList;
import java.util.List;

public class ConcertMapper {

    public static List<ScheduleResDTO> toDtoFromSchedule(List<Schedule> schedules) {
        if (schedules.size() == 0) {
            return null;
        }
        List<ScheduleResDTO> dto = new ArrayList<>();
        for (Schedule entity : schedules) {
            ScheduleResDTO resVo = new ScheduleResDTO().builder()
                .scheduleId(entity.getScheduleId())
                .concertId(entity.getConcertId())
                .scheduleDate(entity.getScheduleDate())
                .totalSeat(entity.getTotalSeat())
                .build();
            dto.add(resVo);
        }
        return dto;
    }

    public static List<SeatResDTO> toDtoFromSeat(List<Seat> seats) {
        if (seats.size() == 0) {
            return null;
        }
        List<SeatResDTO> dto = new ArrayList<>();
        for (Seat entity : seats) {
            SeatResDTO resVo = new SeatResDTO().builder()
                .seatId(entity.getSeatId())
                .seatStatus(entity.getSeatStatus())
                .seatNumber(entity.getSeatNumber())
                .scheduleId(entity.getScheduleId())
                .price(entity.getPrice())
                .build();
            dto.add(resVo);
        }
        return dto;
    }

    public static List<ConcertResDTO> toDtoFromConcert(List<Concert> concerts) {
        if (concerts.size() == 0) {
            return null;
        }
        List<ConcertResDTO> dto = new ArrayList<>();
        for (Concert entity : concerts) {
            ConcertResDTO resVo = new ConcertResDTO().builder()
                .concertId(entity.getConcertId())
                .title(entity.getTitle())
                .build();
            dto.add(resVo);
        }
        return dto;
    }

}
