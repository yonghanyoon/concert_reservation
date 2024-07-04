package com.hhplus.concert.presentation.dto.response;

import com.hhplus.concert.infrastructure.type.TokenStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthTokenGetResVo {
    private Integer position;
    private TokenStatus tokenStatus;
    private LocalDateTime expirationTime;
}
