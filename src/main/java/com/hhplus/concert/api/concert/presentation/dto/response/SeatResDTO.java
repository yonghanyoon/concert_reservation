package com.hhplus.concert.api.concert.presentation.dto.response;

import com.hhplus.concert.api.concert.domain.type.SeatStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "예약 가능 좌석 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SeatResDTO {
    @Schema(description = "좌석 ID")
    private Long seatId;
    @Schema(description = "좌석 상태")
    private SeatStatus seatStatus;
    @Schema(description = "좌석 번호")
    private Long seatNumber;
    @Schema(description = "날짜 ID")
    private Long scheduleId;
    @Schema(description = "가격")
    private Long price;
}
