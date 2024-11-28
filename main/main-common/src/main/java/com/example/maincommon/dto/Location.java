package com.example.maincommon.dto;

import lombok.Data;

@Data
public class Location {
    private Float lat;
    private Float lon;

    public Location(Float lat, Float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
