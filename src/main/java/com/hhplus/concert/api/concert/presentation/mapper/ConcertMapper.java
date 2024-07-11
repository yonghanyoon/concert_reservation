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
            ScheduleResDTO resVo = new ScheduleResDTO();
            resVo.setScheduleId(entity.getScheduleId());
            resVo.setConcertId(entity.getConcertId());
            resVo.setScheduleDate(entity.getScheduleDate());
            resVo.setTotalSeat(entity.getTotalSeat());
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
            SeatResDTO resVo = new SeatResDTO();
            resVo.setSeatId(entity.getSeatId());
            resVo.setSeatStatus(entity.getSeatStatus());
            resVo.setSeatNumber(entity.getSeatNumber());
            resVo.setScheduleId(entity.getScheduleId());
            resVo.setPrice(entity.getPrice());
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
            ConcertResDTO resVo = new ConcertResDTO();
            resVo.setConcertId(entity.getConcertId());
            resVo.setTitle(entity.getTitle());
            dto.add(resVo);
        }
        return dto;
    }

}
