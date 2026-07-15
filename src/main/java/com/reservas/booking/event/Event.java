package com.reservas.booking.event;

import com.reservas.booking.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Instant startsAt;

    @NotNull
    @Column(nullable = false)
    private Instant endsAt;

    @NotNull
    @Column(nullable = false)
    private UUID venueId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status = EventStatus.DRAFT;

    @NotBlank
    @Column(nullable = false, updatable = false)
    private String createdBy;

    protected Event() {
    }

    public Event(String name, Instant startsAt, Instant endsAt, UUID venueId, String createdBy) {
        if (endsAt.isBefore(startsAt)) {
            throw new IllegalArgumentException("endsAt precisa ser depois de startsAt");
        }
        this.name = name;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.venueId = venueId;
        this.createdBy = createdBy;
    }


    public String getName() {
        return name;
    }

    public Instant getStartsAt() {
        return startsAt;
    }

    public Instant getEndsAt() {
        return endsAt;
    }

    public UUID getVenueId() {
        return venueId;
    }

    public EventStatus getStatus() {
        return status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void publish() {
        if (this.status != EventStatus.DRAFT) {
            throw new IllegalStateException("So e possivel publicar um evento em DRAFT, status atual: " + status);
        }
        this.status = EventStatus.PUBLISHED;
    }

    public void cancel() {
        if (this.status == EventStatus.CANCELLED) {
            throw new IllegalStateException("Evento ja esta cancelado");
        }
        this.status = EventStatus.CANCELLED;
    }

}