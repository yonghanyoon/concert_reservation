package com.hhplus.concert.api.reservation.domain.event;

import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import java.util.List;

public class ReservationEvent {

    private Long messageId;
    private List<Long> seatIds;
    public ReservationEvent() {
    }
    public ReservationEvent(Long messageId, List<Long> seatIds) {
        this.messageId = messageId;
        this.seatIds = seatIds;
    }

    public Long getMessageId() {
        return messageId;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }
}
