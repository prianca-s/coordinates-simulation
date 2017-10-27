package com.locus.project.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 *         on 26/10/17.
 */
public class DistanceUtil {
    private static Date currDate;

    public static double getDistanceFromLatLon(double lat1,double lon1, double lat2,double lon2, double el1, double el2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance)      ;
    }

    public static double calculateBearing(double lat1, double lng1, double lat2, double lng2) {
//      calculates the azimuth in degrees from start point to end point'''
        double startLat = Math.toRadians(lat1);
        double startLong = Math.toRadians(lng1);
        double endLat = Math.toRadians(lat2);
        double endLong = Math.toRadians(lng2);
        double dLong = endLong - startLong;
        double dPhi = Math.log(Math.tan(endLat / 2.0 + Math.PI / 4.0) / Math.tan(startLat / 2.0 + Math.PI / 4.0));
        if (Math.abs(dLong) > Math.PI) {
            if (dLong > 0.0)
                dLong = -(2.0 * Math.PI - dLong);
            else
                dLong = (2.0 * Math.PI + dLong);
        }
        double bearing = (Math.toDegrees(Math.atan2(dLong, dPhi)) + 360.0) % 360.0;

        return bearing;
    }

    public static List<Double> getDestinationLatLong(double lat, double lng, double azimuth, double distance) {
//      returns the lat an long of destination point given the start lat, long, aziuth, and distance'''
        double R = 6378.1; //Radius of the Earth in km
        double brng = Math.toRadians(azimuth); //Bearing is degrees converted to Math.toRadians.
        double d = distance / 1000; //Distance m converted to km

        double lat1 = Math.toRadians(lat); //Current dd lat point converted to Math.toRadians
        double lon1 = Math.toRadians(lng); //Current dd long point converted to Math.toRadians

        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));

        double lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));

        lat2 = Math.toDegrees(lat2);
        lon2 = Math.toDegrees(lon2);

        List<Double> resList = new ArrayList<>();
        resList.add((double) Math.round(lat2*100000)/100000);
        resList.add((double) Math.round(lon2*100000)/100000);
        return resList;
    }
}
