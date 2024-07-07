package com.hhplus.concert.api.balance.presentation.dto.request;

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
}
