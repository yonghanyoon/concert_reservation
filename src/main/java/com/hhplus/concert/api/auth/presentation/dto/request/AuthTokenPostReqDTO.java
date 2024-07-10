package com.hhplus.concert.api.auth.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthTokenPostReqDTO {
    @Positive
    @NotNull(message = "userId는 null일 수 없습니다.")
    private Long userId;
}
