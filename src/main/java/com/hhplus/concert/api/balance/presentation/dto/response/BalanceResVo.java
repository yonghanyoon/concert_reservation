package com.hhplus.concert.api.balance.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "유저 잔액 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BalanceResVo {
    @Schema(description = "사용자 ID")
    private Long userId;
    @Schema(description = "잔액")
    private Long amount;
}
