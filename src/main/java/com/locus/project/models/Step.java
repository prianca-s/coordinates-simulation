package com.locus.project.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class Step implements Serializable {
    private Distance distance;
    private Duration duration;
    private Coordinate start_location;
    private Coordinate end_location;
    private String html_instructions;
    private PolyLine polyline;
    private String maneuver;
    private String travel_mode;
}
