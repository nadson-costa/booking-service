package com.reservas.booking.seat;

import com.reservas.booking.common.BaseEntity;
import com.reservas.booking.event.EventStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "seats")
public class Seat extends BaseEntity {
    @NotNull
    @Column(nullable = false)
    private UUID eventId;

    @NotBlank
    @Column(nullable = false)
    private String section;

    @NotBlank
    @Column(nullable = false)
    private String rowLabel;

    @NotNull
    @Column(nullable = false)
    private Integer number;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @Column
    private Instant heldUntil;

    @Version
    @Column(nullable = false)
    private Long version;

    public Seat() {
    }

    public Seat(UUID eventId, String section, String rowLabel, Integer number, SeatStatus status, Instant heldUntil, Long version) {
        this.eventId = eventId;
        this.section = section;
        this.rowLabel = rowLabel;
        this.number = number;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getSection() {
        return section;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public Number getNumber() {
        return number;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public Instant getHeldUntil() {
        return heldUntil;
    }

    public Number getVersion() {
        return version;
    }

    public void hold(Instant heldUntil) {
        if (this.status != SeatStatus.AVAILABLE) {
            throw new IllegalStateException("So e possivel segurar um assento AVAILABLE, status atual: " + status);
        }
        this.status = SeatStatus.HELD;
        this.heldUntil = heldUntil;
    }

    public void book() {
        if (this.status != SeatStatus.HELD) {
            throw new IllegalStateException("So e possivel confirmar um assento HELD, status atual: " + status);
        }
        this.status = SeatStatus.BOOKED;
        this.heldUntil = null;
    }

    public void release() {
        if (this.status == SeatStatus.AVAILABLE) {
            throw new IllegalStateException("Assento ja esta disponivel");
        }
        this.status = SeatStatus.AVAILABLE;
        this.heldUntil = null;
    }
}
