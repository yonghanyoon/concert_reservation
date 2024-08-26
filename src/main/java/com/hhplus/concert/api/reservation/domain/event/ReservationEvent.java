package com.hhplus.concert.api.reservation.domain.event;

import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import java.util.List;

public class ReservationEvent {

    private String uuid;
    private List<Long> seatIds;
    private Reservation reservation;

    public ReservationEvent() {
    }
    public ReservationEvent(String uuid, List<Long> seatIds) {
        this.uuid = uuid;
        this.seatIds = seatIds;
    }

    public ReservationEvent(String uuid, Reservation reservation) {
        this.uuid = uuid;
        this.seatIds = reservation.getReservationSeats().stream().map(ReservationSeat::getSeatId).toList();
        this.reservation = reservation;
    }

    public String getUuid() {
        return uuid;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
