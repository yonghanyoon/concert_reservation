package com.hhplus.concert.payment.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentResVo {
    private Long paymentId;
    private LocalDateTime paymentTime;
}
