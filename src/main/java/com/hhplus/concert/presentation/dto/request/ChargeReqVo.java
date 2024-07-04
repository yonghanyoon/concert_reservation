package com.hhplus.concert.presentation.dto.request;

import com.hhplus.concert.infrastructure.type.PaymentType;
import java.time.LocalDateTime;
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
