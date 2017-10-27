package com.locus.project.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */
@Data
public class GeocodedWaypoint implements Serializable {
    private String geocoder_status;
    private String place_id;
    List<String> types;
}
