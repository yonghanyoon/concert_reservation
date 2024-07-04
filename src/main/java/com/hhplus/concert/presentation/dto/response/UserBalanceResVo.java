package com.hhplus.concert.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserBalanceResVo {
    private Long userId;
    private Long balance;
}
