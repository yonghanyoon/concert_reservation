package com.hhplus.concert.api.concert.presentation.dto.response;

import com.hhplus.concert.api.concert.domain.entity.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ScheduleResVo {
    private Long scheduleId;
    private Long concertId;
    private LocalDateTime scheduleDate;
    private Long totalSeat;
}
