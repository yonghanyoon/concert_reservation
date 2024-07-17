package com.hhplus.concert.api.reservation.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
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
public class ReservationResDTO {
    @Schema(description = "예약 ID")
    private Long reservationId;
    @Schema(description = "콘서트 제목")
    private String concertTitle;
    @Schema(description = "사용자 ID")
    private Long userId;
    @Schema(description = "예약 좌석 리스트")
    private List<Long> seatIdList;
    @Schema(description = "총 가격")
    private Long totalPrice;
    @Schema(description = "만료 시간")
    private LocalDateTime reservationExpiry;
}
