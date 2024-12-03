package ru.practicum.mainserver.dto.event;

import lombok.Data;

@Data
public class LocationDto {
    private Float lat;
    private Float lon;

    public LocationDto(Float lat, Float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}