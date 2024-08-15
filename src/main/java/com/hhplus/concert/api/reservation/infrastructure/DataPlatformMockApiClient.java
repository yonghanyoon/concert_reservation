package com.hhplus.concert.api.reservation.infrastructure;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataPlatformMockApiClient {

    public void sendData(List<Long> seatIds) {
        try {
            log.info("[DataPlatform] : 좌석 예약 데이터 저장 성공 => " + seatIds);
        } catch (Exception e) {
            log.error("[DataPlatform] : 좌석 예약 데이터 저장 실패 => " + e.getMessage());
        }
    }
}
