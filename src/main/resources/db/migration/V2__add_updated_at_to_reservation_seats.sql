-- V2: adiciona updated_at em reservation_seats
--
-- Faltou na V1: o ReservationSeat.java estende BaseEntity, que exige
-- updated_at (via @LastModifiedDate), mas a tabela so tinha created_at.
-- Correcao de um erro meu na V1.

ALTER TABLE reservation_seats
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE reservation_seats
    ALTER COLUMN updated_at DROP DEFAULT;