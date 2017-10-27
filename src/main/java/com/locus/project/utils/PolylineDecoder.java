package com.locus.project.utils;

import com.locus.project.models.Coordinate;

import java.util.ArrayList;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 *         on 26/10/17.
 */
public class PolylineDecoder {

    public static ArrayList decodePolyLine(String encoded) {
        ArrayList poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            Coordinate p = new Coordinate();
            double x = (((double) lat / 1E5));
            double y = (((double) lng / 1E5));


            p.setLat(x);
            p.setLng(y);

            poly.add(p);
        }
        return poly;
    }

}
