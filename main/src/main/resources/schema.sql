CREATE TABLE IF NOT EXISTS users(
    id SERIAL NOT NULL UNIQUE,
    name VARCHAR(250) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories(
    id serial NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events(
    id SERIAL NOT NULL UNIQUE,
    category_id BIGINT NOT NULL,
    initiator_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    annotation VARCHAR(2000) NOT NULL,
    description VARCHAR(7000) NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    confirmed_requests BIGINT DEFAULT 0,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    paid BOOLEAN NOT NULL,
    participant_limit BIGINT,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state VARCHAR(255),
    title VARCHAR(120) NOT NULL,
    views BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS locations(
    id SERIAL NOT NULL UNIQUE,
    lat DOUBLE PRECISION NOT NULL,
    lon DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations(
    id SERIAL NOT NULL UNIQUE,
    pinned BOOLEAN,
    title VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations_events(
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    PRIMARY KEY (compilation_id, event_id)
);
CREATE TABLE IF NOT EXISTS participations (
    id SERIAL NOT NULL UNIQUE,
    event_id BIGINT NOT NULL,
    requester_id BIGINT NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
);



ALTER TABLE events ADD CONSTRAINT "events_fk1" FOREIGN KEY (category_id) REFERENCES categories(id);

ALTER TABLE events ADD CONSTRAINT "events_fk2" FOREIGN KEY (initiator_id) REFERENCES users(id);

ALTER TABLE events ADD CONSTRAINT "events_fk3" FOREIGN KEY (location_id) REFERENCES locations(id);

ALTER TABLE compilations_events ADD CONSTRAINT "compilations_events_fk0" FOREIGN KEY (compilation_id) REFERENCES compilations(id);

ALTER TABLE compilations_events ADD CONSTRAINT "compilations_events_fk1" FOREIGN KEY (event_id) REFERENCES events(id);

ALTER TABLE participations ADD CONSTRAINT "participations_fk1" FOREIGN KEY (event_id) REFERENCES events(id);

ALTER TABLE participations ADD CONSTRAINT "participations_fk2" FOREIGN KEY (requester_id) REFERENCES users(id);