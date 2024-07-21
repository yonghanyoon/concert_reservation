package com.hhplus.concert.api.balance.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "유저 잔액 충전 요청 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChargeReqVo {
    @Schema(description = "사용자 ID")
    @Positive
    @NotNull(message = "userId는 null일 수 없습니다.")
    private Long userId;
    @Schema(description = "잔액")
    @Positive
    private Long amount;
}
