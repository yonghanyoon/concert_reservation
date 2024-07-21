package com.hhplus.concert.api.concert.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "콘서트 조회 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConcertResDTO {
    @Schema(description = "콘서트 ID")
    private Long concertId;
    @Schema(description = "콘서트 제목")
    private String title;
}
