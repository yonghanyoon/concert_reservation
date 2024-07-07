package com.hhplus.concert.api.reservation.presentation.dto.response;

import com.hhplus.concert.api.reservation.domain.Schedule;
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
    private List<Schedule> scheduleList;
}
