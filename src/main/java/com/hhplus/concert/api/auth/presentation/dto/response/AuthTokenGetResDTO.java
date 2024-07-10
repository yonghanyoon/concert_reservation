package com.hhplus.concert.api.auth.presentation.dto.response;

import com.hhplus.concert.api.auth.domain.type.TokenStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthTokenGetResDTO {
    private Integer position;
    private TokenStatus tokenStatus;
    private LocalDateTime expirationTime;
}
