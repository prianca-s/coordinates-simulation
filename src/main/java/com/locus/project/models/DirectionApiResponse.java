package com.locus.project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DirectionApiResponse implements Serializable {
    private List<GeocodedWaypoint> geocoded_waypoints;
    private List<Route> routes;
    private String status;
}
