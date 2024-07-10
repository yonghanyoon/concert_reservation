package com.hhplus.concert.api.reservation.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationResVo {
    private Long reservationId;
    private List<Long> seatIdList;
    private Long totalPrice;
    private LocalDateTime reservationExpiry;
}
