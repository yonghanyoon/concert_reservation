package com.hhplus.concert.api.balance.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BalanceResVo {
    private Long userId;
    private Long amount;
}
