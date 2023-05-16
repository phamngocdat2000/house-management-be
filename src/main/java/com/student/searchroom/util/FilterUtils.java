package com.student.searchroom.util;

import com.student.searchroom.solr.entity.HouseSolr;

import java.util.List;
import java.util.stream.Collectors;

public final class FilterUtils {

    public static List<HouseSolr> filterByDistance(List<HouseSolr> houses, Double latSource, Double lnpSource, Double distance) {
        if (distance == null) return houses;
        return houses.stream()
                .filter(house -> isMatch(latSource, house.getLat(), lnpSource, house.getLnp(), distance))
                .collect(Collectors.toList());
    }

    private static boolean isMatch(double latSource, String lat2, double lnpSource,
                            String lnp2 , double distance) {
        double lat2d = Double.parseDouble(lat2);
        double lnp2d = Double.parseDouble(lnp2);
        return distance >= distance(latSource, lat2d, lnpSource, lnp2d);
    }

    public static double distance(double lat1, double lat2, double lon1,
                            double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
