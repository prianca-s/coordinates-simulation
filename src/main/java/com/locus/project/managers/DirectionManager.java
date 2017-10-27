package com.locus.project.managers;

import com.locus.project.models.LocationRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */
public interface DirectionManager {
    boolean sendDirectionData(LocationRequest locationRequest) throws IOException, URISyntaxException, ExecutionException, TimeoutException, InterruptedException;
}
