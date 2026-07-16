package com.reservas.booking.reservation;

import com.reservas.booking.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.PENDING;

    @NotNull
    @Column(nullable = false)
    private Instant expiresAt;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationSeat> seats = new ArrayList<>();


    protected Reservation() {
    }

    public Reservation(String email, Instant expiresAt) {
        this.email = email;
        this.expiresAt = expiresAt;
    }

    public String getEmail() {
        return email;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void reserveSeat(UUID seatId, BigDecimal pricePaid) {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException("So e possivel adicionar assentos a uma reserva PENDING, status atual: " + status);
        }
        this.seats.add(new ReservationSeat(this, seatId, pricePaid));
    }


    public void confirm() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException("So e possivel confirmar uma reserva PENDING, status atual: " + status);
        }
        this.status = ReservationStatus.CONFIRMED;
    }

    public void expire() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException("So uma reserva PENDING pode expirar, status atual: " + status);
        }
        this.status = ReservationStatus.EXPIRED;
    }

    public void cancel() {
        if (this.status == ReservationStatus.EXPIRED) {
            throw new IllegalStateException("Reserva expirada nao pode ser cancelada");
        }
        this.status = ReservationStatus.CANCELLED;
    }


}
