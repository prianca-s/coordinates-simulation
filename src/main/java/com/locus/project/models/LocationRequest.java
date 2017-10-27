package com.locus.project.models;

import lombok.*;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */

@ToString()
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    @Getter @Setter
    private double originLat;

    @Getter @Setter
    private double originLong;

    @Getter @Setter
    private double destinationLat;

    @Getter @Setter
    private double destinationLong;
}
