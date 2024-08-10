package com.hhplus.concert.api.reservation.domain.event;

import com.hhplus.concert.api.reservation.domain.entity.Reservation;
import com.hhplus.concert.api.reservation.domain.entity.ReservationSeat;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class ReservationEvent extends ApplicationEvent {

    private final Reservation reservation;
    private final List<ReservationSeat> reservationSeats;

    public ReservationEvent(Object source, Reservation reservation,
        List<ReservationSeat> reservationSeats) {
        super(source);
        this.reservation = reservation;
        this.reservationSeats = reservationSeats;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public List<ReservationSeat> getReservationSeats() {
        return reservationSeats;
    }
}
