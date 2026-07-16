CREATE TABLE venues (
                        id         UUID PRIMARY KEY,
                        name       VARCHAR(255) NOT NULL,
                        address    VARCHAR(255) NOT NULL,
                        zip_code   VARCHAR(20)  NOT NULL,
                        capacity   INTEGER      NOT NULL CHECK (capacity > 0),
                        created_at TIMESTAMPTZ  NOT NULL,
                        updated_at TIMESTAMPTZ  NOT NULL
);

CREATE TABLE events (
                        id         UUID PRIMARY KEY,
                        name       VARCHAR(255) NOT NULL,
                        starts_at  TIMESTAMPTZ  NOT NULL,
                        ends_at    TIMESTAMPTZ  NOT NULL,
                        venue_id   UUID         NOT NULL REFERENCES venues (id),
                        status     VARCHAR(20)  NOT NULL,
                        created_by VARCHAR(255) NOT NULL,
                        created_at TIMESTAMPTZ  NOT NULL,
                        updated_at TIMESTAMPTZ  NOT NULL,

                        CONSTRAINT chk_events_dates CHECK (ends_at > starts_at)
);

CREATE INDEX idx_events_venue_id ON events (venue_id);

CREATE TABLE seats (
                       id         UUID PRIMARY KEY,
                       event_id   UUID        NOT NULL REFERENCES events (id),
                       section    VARCHAR(50) NOT NULL,
                       row_label  VARCHAR(10) NOT NULL,
                       number     INTEGER     NOT NULL CHECK (number > 0),
                       status     VARCHAR(20) NOT NULL,
                       held_until TIMESTAMPTZ NULL,
                       version    BIGINT      NOT NULL DEFAULT 0,
                       created_at TIMESTAMPTZ NOT NULL,
                       updated_at TIMESTAMPTZ NOT NULL,

                       CONSTRAINT uq_seats_position UNIQUE (event_id, section, row_label, number),

                       CONSTRAINT chk_seats_held_until CHECK (
                           (status = 'HELD' AND held_until IS NOT NULL) OR
                           (status <> 'HELD' AND held_until IS NULL)
                           )
);

CREATE INDEX idx_seats_event_status ON seats (event_id, status);

CREATE TABLE reservations (
                              id         UUID        PRIMARY KEY,
                              email      VARCHAR(255) NOT NULL,
                              status     VARCHAR(20)  NOT NULL,
                              expires_at TIMESTAMPTZ  NOT NULL,
                              created_at TIMESTAMPTZ  NOT NULL,
                              updated_at TIMESTAMPTZ  NOT NULL
);

CREATE INDEX idx_reservations_status_expires_at ON reservations (status, expires_at);

CREATE TABLE reservation_seats (
                                   id             UUID           PRIMARY KEY,
                                   reservation_id UUID           NOT NULL REFERENCES reservations (id),
                                   seat_id        UUID           NOT NULL REFERENCES seats (id),
                                   price_paid     NUMERIC(10, 2) NULL,
                                   created_at     TIMESTAMPTZ    NOT NULL,

                                   CONSTRAINT uq_reservation_seats_pair UNIQUE (reservation_id, seat_id)
);

CREATE INDEX idx_reservation_seats_seat_id ON reservation_seats (seat_id);