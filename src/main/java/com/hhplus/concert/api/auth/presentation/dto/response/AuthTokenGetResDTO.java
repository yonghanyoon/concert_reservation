package com.hhplus.concert.api.auth.presentation.dto.response;

import com.hhplus.concert.api.auth.domain.type.TokenStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "대기열 토큰 조회 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthTokenGetResDTO {
    @Schema(description = "대기 번호")
    private Long position;
    @Schema(description = "토큰 상태")
    private TokenStatus tokenStatus;
    @Schema(description = "만료 시간")
    private LocalDateTime expirationTime;
}
