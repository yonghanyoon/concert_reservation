package com.hhplus.concert.presentation.dto.response;

import com.hhplus.concert.domain.Schedule;
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
