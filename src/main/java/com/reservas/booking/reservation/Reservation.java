package com.reservas.booking.reservation;

import com.reservas.booking.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

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
