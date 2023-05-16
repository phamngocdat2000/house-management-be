package com.student.searchroom.util;

import com.student.searchroom.model.Coordinate;
import com.student.searchroom.solr.entity.HouseSolr;

import java.util.ArrayList;
import java.util.List;

public final class Calculator {


    private static Coordinate calculateMidPoint(Coordinate[] coordinates){

        double lat1 = coordinates[0].getLat();
        double lon1 = coordinates[0].getLng();
        double lat2 = coordinates[1].getLat();
        double lon2 = coordinates[1].getLng();
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        return new Coordinate(Math.toDegrees(lat3), Math.toDegrees(lon3));
    }
    

    public static Coordinate calculateCenterCoordinate(List<HouseSolr> housesSolr) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (HouseSolr houseSolr : housesSolr) {
            coordinates.add(new Coordinate(houseSolr.getLat(), houseSolr.getLnp()));
        }
        if (housesSolr.isEmpty())
            return new Coordinate(0.0, 0.0);
        if (housesSolr.size() < 2)
            return new Coordinate(coordinates.get(0).getLat(), coordinates.get(0).getLng());
        return calculateMidPoint(findPointInBorder(coordinates));
    }

    public static void main(String[] args) {
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(21.024312, 105.792343));
        System.out.println(calculateMidPoint(findPointInBorder(coordinates)));
    }

    private static Coordinate[] findPointInBorder(List<Coordinate> coordinates) {
        Coordinate maxLat = coordinates.get(0);
        Coordinate minLat = coordinates.get(0);
        Coordinate maxLon = coordinates.get(0);
        Coordinate minLon = coordinates.get(0);
        for (Coordinate coordinate : coordinates) {
            if (maxLat.getLat() < coordinate.getLat())
                maxLat = coordinate;
            if (minLat.getLat() > coordinate.getLat())
                minLat = coordinate;
            if (maxLon.getLng() < coordinate.getLng())
                maxLon = coordinate;
            if (minLon.getLng() > coordinate.getLng())
                minLon = coordinate;
        }
        double distanceOfMaxLat = FilterUtils.distance(maxLat.getLat(), maxLat.getLng(), minLat.getLat(), minLat.getLng());
        double distanceOfMinLon = FilterUtils.distance(maxLon.getLat(), maxLon.getLng(), minLon.getLat(), minLon.getLng());
        
        return distanceOfMaxLat > distanceOfMinLon ? new Coordinate[] {maxLat, minLat} : new Coordinate[] {maxLon, minLon}; 
    }

}
