package com.hhplus.concert.api.reservation.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentResDTO {
    private Long paymentId;
    private LocalDateTime paymentTime;
}
