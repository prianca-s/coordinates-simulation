package com.locus.project.config;

import lombok.Data;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of coordinates-simulation
 *         on 27/10/17.
 */

@Data
public class Internal {
    private String userId;
    private String clientId;
    private String hostname;
    private String password;
    private String clientPath;
    private String userPath;
    private String locationPath;
}
