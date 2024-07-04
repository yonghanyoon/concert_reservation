package com.hhplus.concert.presentation.dto.request;

import com.hhplus.concert.infrastructure.type.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentReqVo {
    private Long userId;
    private PaymentType type;
    private Long reservationId;
}
