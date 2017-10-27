package com.locus.project.models;

import lombok.Data;

import java.io.Serializable;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */

@Data
public class Coordinate implements Serializable {
    private double lat;
    private double lng;
}
