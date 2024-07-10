package com.hhplus.concert.api.concert.presentation.mapper;

import com.hhplus.concert.api.concert.domain.entity.Schedule;
import com.hhplus.concert.api.concert.domain.entity.Seat;
import com.hhplus.concert.api.concert.presentation.dto.response.ScheduleResVo;
import com.hhplus.concert.api.concert.presentation.dto.response.SeatResVo;
import java.util.ArrayList;
import java.util.List;

public class ConcertMapper {

    public static List<ScheduleResVo> toDtoFromSchedule(List<Schedule> schedule) {
        if (schedule.size() == 0) {
            return null;
        }
        List<ScheduleResVo> dto = new ArrayList<>();
        for (Schedule entity : schedule) {
            ScheduleResVo resVo = new ScheduleResVo();
            resVo.setScheduleId(entity.getScheduleId());
            resVo.setConcertId(entity.getConcertId());
            resVo.setScheduleDate(entity.getScheduleDate());
            resVo.setTotalSeat(entity.getTotalSeat());
            dto.add(resVo);
        }
        return dto;
    }

    public static List<SeatResVo> toDtoFromSeat(List<Seat> seat) {
        if (seat.size() == 0) {
            return null;
        }
        List<SeatResVo> dto = new ArrayList<>();
        for (Seat entity : seat) {
            SeatResVo resVo = new SeatResVo();
            resVo.setSeatId(entity.getSeatId());
            resVo.setSeatStatus(entity.getSeatStatus());
            resVo.setSeatNumber(entity.getSeatNumber());
            resVo.setScheduleId(entity.getScheduleId());
            resVo.setPrice(entity.getPrice());
            dto.add(resVo);
        }
        return dto;
    }

}
