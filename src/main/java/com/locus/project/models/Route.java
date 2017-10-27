package com.locus.project.models;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Route implements Serializable {
    private Bound bounds;
    private String copyrights;
    private List<Leg> legs;
    private Map<String, String> overview_polyline;
    private String summary;
    private ArrayList warnings;
    private List<Integer> waypoint_order;
}
