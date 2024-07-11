package com.hhplus.concert.api.reservation.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "좌석 예약 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationReqDTO {
    @Schema(description = "사용자 ID")
    private Long userId;
    @Schema(description = "콘서트 ID")
    private Long contentId;
    @Schema(description = "날짜 ID")
    private Long scheduleId;
    @Schema(description = "예약 좌석 리스트")
    private List<Long> seatIdList;
}
