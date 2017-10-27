package com.locus.project.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 *         on 27/10/17.
 */

@NoArgsConstructor
@AllArgsConstructor
public class PolylineData {
    @Getter @Setter
    private double lat;
    @Getter @Setter
    private double lng;
    @Getter @Setter
    private String timestamp;


}
