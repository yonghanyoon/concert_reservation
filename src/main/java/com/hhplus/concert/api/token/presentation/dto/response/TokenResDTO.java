package com.hhplus.concert.api.token.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "대기열 토큰 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TokenResDTO {
    @Schema(description = "사용자 ID")
    private Long userId;
    @Schema(description = "대기 번호")
    private Long position;
    @Schema(description = "대기 예상 시간")
    private String waitingTime;
}
