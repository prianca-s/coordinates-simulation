package com.locus.project.managerImpl;

import com.locus.project.exceptions.BusinessException;
import com.locus.project.handlers.ApiHandler;
import com.locus.project.handlers.impl.ApiHandlerImpl;
import com.locus.project.managers.DirectionManager;
import com.locus.project.models.*;
import com.locus.project.utils.DistanceUtil;
import com.locus.project.utils.PolylineDecoder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.locus.project.utils.CommonUtils.TIME_IN_SEC;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.REQUEST_TIMEOUT;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 */
public class DirectionManagerImpl implements DirectionManager {
    ApiHandler apiHandler = ApiHandlerImpl.getInstance();

    public DirectionManagerImpl() {
    }

    public boolean sendDirectionData(LocationRequest locationRequest) throws IOException, URISyntaxException, ExecutionException, TimeoutException, InterruptedException {
        String origin = String.valueOf(locationRequest.getOriginLat()) + "," + String.valueOf(locationRequest.getOriginLong());
        String destination = String.valueOf(locationRequest.getDestinationLat()) + "," + String.valueOf(locationRequest.getDestinationLong());;

        DirectionApiRequest directionApiRequest = new DirectionApiRequest(origin, destination, null);
        DirectionApiResponse directionApiResponse;
        try {
            directionApiResponse = apiHandler.getDirections(directionApiRequest);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Timeout occur while getting directionApiResponse"+ e);
            throw new BusinessException("Timeout occur while getting directionApiResponse", REQUEST_TIMEOUT);
        } catch (URISyntaxException e) {
            System.out.println("Exception occur: "+ e);
            e.printStackTrace();
            throw new BusinessException("Syntax exception error", REQUEST_TIMEOUT);
        }
        simulateGPSData(directionApiResponse);

        return false;
    }

    /**
     * ONLY ONE LOCATION AND ONE DESTINATION IS ASSUMED IN DirectionApiResponse
    * */
    private void simulateGPSData(DirectionApiResponse directionApiResponse) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        if (directionApiResponse == null)
        {
            System.out.println("NO DATA AVAILABLE FOR SIMULATION");
                throw new BusinessException("No data available for simulation", NOT_FOUND);
        }
        int first = 0;
        float totalDistance, totalTime, avgSpeed;
        if (!directionApiResponse.getRoutes().isEmpty()) {
            Route route = directionApiResponse.getRoutes().get(first);
            if (!route.getLegs().isEmpty()) {

                // FIND STEPS LIST
                List<Step> steps = route.getLegs().get(first).getSteps();
                List<PolylineData> polylineDataList = new ArrayList<>();

                for (Step step : steps) {
                    totalDistance = step.getDistance().getValue();
                    totalTime = step.getDuration().getValue();
//                  IN m/sec
                    avgSpeed = totalDistance/totalTime;

                    List<Coordinate> polyLineCoordinates = PolylineDecoder.decodePolyLine(step.getPolyline().getPoints());
//                    for (Coordinate coordinate : polyLineCoordinates) {
//                        System.out.println(coordinate.getLat()+","+coordinate.getLng());
//                    }
                    DateTime date = new DateTime();
                    DateTime currTime = date.toDateTime( DateTimeZone.UTC );

                    Coordinate startCoordinate = polyLineCoordinates.get(first);
                    for (int j = 1; j < polyLineCoordinates.size(); j++) {
                        Coordinate endCoordinate = polyLineCoordinates.get(j);

                        double distanceOfPolylineLatLng = DistanceUtil.getDistanceFromLatLon(startCoordinate.getLat(), startCoordinate.getLng(), endCoordinate.getLat(), endCoordinate.getLng(), 0, 0);

                        double azumith = DistanceUtil.calculateBearing(startCoordinate.getLat(), startCoordinate.getLng(), endCoordinate.getLat(), endCoordinate.getLng());

                        double distanceForNMinutes = avgSpeed * TIME_IN_SEC; // IN METERS

                        double dist = distanceForNMinutes;

//                      System.out.println("LAT-LONG FOR COORDINATES: " + startCoordinate.getLat() + ", " + startCoordinate.getLng() + ", " + endCoordinate.getLat() + " " + endCoordinate.getLng());
                        System.out.println(startCoordinate.getLat() + "," + startCoordinate.getLng() + "," + currTime);
                        polylineDataList.add(new PolylineData(startCoordinate.getLat(), startCoordinate.getLng(), currTime.toString()));
                        if (dist <= distanceOfPolylineLatLng) {
                            while (dist <= distanceOfPolylineLatLng) {
                                List<Double> latLng = DistanceUtil.getDestinationLatLong(startCoordinate.getLat(), startCoordinate.getLng(), azumith, dist);
                                currTime = currTime.plusSeconds(TIME_IN_SEC);
                                System.out.println(latLng.get(0) + "," + latLng.get(1) + "," + currTime);
                                polylineDataList.add(new PolylineData(latLng.get(0), latLng.get(0), currTime.toString()));

                                dist += distanceForNMinutes;
                            }
                        } else {
                            currTime = currTime.plusMillis((int) totalTime);
                        }
                        startCoordinate = endCoordinate;
                    }
                }
                try {
//                  API CALL TO PUSH DATA TO SERVER
                    pushDataToServer(polylineDataList);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    System.out.println("Timeout occur while getting ApiResponse"+ e);
                    e.printStackTrace();
                    throw new BusinessException("Timeout occur while getting ApiResponse", REQUEST_TIMEOUT);
                }
            }
        }
    }

    private void pushDataToServer(List<PolylineData> polylineDataList) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        for (PolylineData polylineData : polylineDataList) {
            apiHandler.pushPolylineData(polylineData);
        }
    }

}
