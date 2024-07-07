package com.hhplus.concert.api.reservation.presentation.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SeatReqVo {
    private Long userId;
    private Long contentId;
    private Long scheduleId;
    private List<Long> seatIdList;
}
