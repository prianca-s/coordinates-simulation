package com.locus.project.config;

import lombok.Data;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */

@Data
public class ServiceConfig {
    private GoogleCredentials googleApi;
    private Internal internal;
}
