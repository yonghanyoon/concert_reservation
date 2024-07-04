package com.hhplus.concert.payment.presentation.dto.request;

import com.hhplus.concert.payment.domain.type.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChargeReqVo {
    private Long userId;
    private Long amount;
    private PaymentType type;
}
