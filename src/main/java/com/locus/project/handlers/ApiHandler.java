package com.locus.project.handlers;

import com.locus.project.exceptions.BusinessException;
import com.locus.project.models.ApiResponse;
import com.locus.project.models.DirectionApiRequest;
import com.locus.project.models.DirectionApiResponse;
import com.locus.project.models.PolylineData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */
public interface ApiHandler {
    DirectionApiResponse getDirections(DirectionApiRequest directionApiRequest) throws IOException, URISyntaxException, InterruptedException, ExecutionException, TimeoutException;

    ApiResponse pushPolylineData(PolylineData polylineData) throws InterruptedException, ExecutionException, TimeoutException, IOException, BusinessException;
}
