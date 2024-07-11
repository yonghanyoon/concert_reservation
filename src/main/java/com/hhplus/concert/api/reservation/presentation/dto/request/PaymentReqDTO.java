package com.hhplus.concert.api.reservation.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentReqDTO {
    private Long userId;
    private Long reservationId;
    private Long amount;
}
