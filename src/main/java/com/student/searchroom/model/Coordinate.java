package com.student.searchroom.model;

import lombok.Data;

@Data
public class Coordinate {
    private double lat;
    private double lng;

    public Coordinate(String lat, String lon) {
        this.lat = Double.valueOf(lat);
        this.lng = Double.valueOf(lon);
    }

    public Coordinate(double lat, double lon) {
        this.lat = lat;
        this.lng = lon;
    }
}
