package com.hhplus.concert.api.reservation.presentation.dto.response;

import com.hhplus.concert.api.reservation.domain.Seat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SeatResVo {
    private List<Seat> seatList;
}
