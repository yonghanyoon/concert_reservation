package com.hhplus.concert.api.token.interfaces.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "대기열 토큰 발급 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TokenPostResDTO {
    @Schema(description = "사용자 UUID")
    private String uuid;
}
