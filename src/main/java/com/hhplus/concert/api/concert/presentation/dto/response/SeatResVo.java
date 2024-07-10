package com.hhplus.concert.api.concert.presentation.dto.response;

import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SeatResVo {
    private Long seatId;
    private SeatStatus seatStatus;
    private Long seatNumber;
    private Long scheduleId;
    private Long price;
}
