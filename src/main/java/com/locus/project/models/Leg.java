package com.locus.project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leg implements Serializable {
    private Distance distance;
    private Duration duration;
    private String end_address;
    private Coordinate end_location;
    private String start_address;
    private Coordinate start_location;
    List<Step> steps;
//    private ArrayList traffic_speed_entry;
//    private ArrayList via_waypoint;

}
