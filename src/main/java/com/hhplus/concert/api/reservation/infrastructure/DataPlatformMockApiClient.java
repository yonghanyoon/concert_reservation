package com.hhplus.concert.api.reservation.infrastructure;

import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataPlatformMockApiClient {

    public void sendData(Reservation reservation, List<ReservationSeat> reservationSeats) {
        try {
            log.info("[DataPlatform] : 좌석 예약 데이터 저장 성공 => " + reservationSeats.stream().map(ReservationSeat::getSeatId).toList());
        } catch (Exception e) {
            log.error("[DataPlatform] : 좌석 예약 데이터 저장 실패 => " + e.getMessage());
        }
    }
}
