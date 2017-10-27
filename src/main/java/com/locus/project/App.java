package com.locus.project;

import com.locus.project.managerImpl.DirectionManagerImpl;
import com.locus.project.managers.DirectionManager;
import com.locus.project.models.LocationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public App() {
    }

    public static void main(String[] args ) throws IOException, URISyntaxException, ExecutionException, TimeoutException, InterruptedException, ParseException {
        DirectionManager directionManager = new DirectionManagerImpl();

        Scanner scan = new Scanner(System.in);
        double originLat, originLong,  destinationLat, destinationLong;
        String mode;
        long departureTime;
        int option;


        System.out.println("PRESS 0 FOR - ORIGIN AND DESTINATION ENDPOINTS\n" +
                "1 FOR - ORIGIN AND DESTINATION LOCATIONS NAME\n" +
                "2 FOR - CSV FILE");

        option = scan.nextInt();

        switch (option) {
            case 0:
                System.out.println("ENTER THE ORIGIN LAT-LONG: ");
                originLat = scan.nextDouble();
                originLong = scan.nextDouble();

                System.out.println("ENTER THE DESTINATION LAT-LONG: ");
                destinationLat = scan.nextDouble();
                destinationLong = scan.nextDouble();

                LocationRequest locationRequest = new LocationRequest(originLat, originLong, destinationLat, destinationLong);
                directionManager.sendDirectionData(locationRequest);

                break;
            case 1:
//                  TODO: CAN BE FURTHER IMPLEMENTED
                System.out.println("ENTER THE ORIGIN AND DESTINATION LOCATION NAME: ");
                break;
            case 2:
//                  TODO: CAN BE FURTHER IMPLEMENTED
                System.out.println("ACCESS CSV FILE");

                break;

        }
    }
}
