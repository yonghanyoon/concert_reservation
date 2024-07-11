package com.hhplus.concert.api.concert.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "예약 가능 날짜 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ScheduleResDTO {
    @Schema(description = "날짜 ID")
    private Long scheduleId;
    @Schema(description = "콘서트 ID")
    private Long concertId;
    @Schema(description = "날짜")
    private LocalDateTime scheduleDate;
    @Schema(description = "총 좌석 수")
    private Long totalSeat;
}
