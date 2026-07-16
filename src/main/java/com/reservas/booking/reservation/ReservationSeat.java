package com.reservas.booking.reservation;

import com.reservas.booking.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "reservation_seats")
public class ReservationSeat extends BaseEntity {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false, updatable = false)
    private Reservation reservation;

    @NotNull
    @Column(nullable = false, updatable = false)
    private UUID seatId;

    @NotNull
    private BigDecimal pricePaid;

    protected ReservationSeat(){

    }

    public ReservationSeat(Reservation reservation, UUID seatId, BigDecimal pricePaid) {
        this.reservation = reservation;
        this.seatId = seatId;
        this.pricePaid = pricePaid;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public UUID getSeatId() {
        return seatId;
    }

    public BigDecimal getPricePaid() {
        return pricePaid;
    }
}
