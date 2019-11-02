package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.drone.DroneState;
import com.vanchutin.jmavlib.GlobalPositionProjector;
import com.vanchutin.jmavlib.LatLonAlt;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

@Slf4j
public class FlightSimulation implements Runnable {

    public FlightSimulation(Drone drone, LatLonAlt home, LatLonAlt destination){
        this.drone = drone;
        this.destination = destination;
        this.home = home;
    }

    private int droneId;
    LatLonAlt home;
    LatLonAlt destination;
    GlobalPositionProjector projector;
    Drone drone;
    DroneState droneState = DroneState.ON_LAND;
    DeliveryState deliveryState = DeliveryState.TO_CLIENT;
    boolean goHome;
    double distanceX, distanceY, distance;
    int takeOffCounter;
    final int  takeOffTime = 10;
    double speedX, speedY, cruiseSpeed = 13.8;//11; // m / s
    double dt = 1;
    double[] destinationLocalCoordinates;
    double[] startLocalCoordinates;
    boolean deliveryCompleted = false;
    int time;






    public void run() {

        projector = new GlobalPositionProjector();

        while(true) {
            update();
            if (deliveryCompleted) {
                // set drone flag as available
            break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

    }

    private void update(){

        time++;

        switch (droneState){
            case ON_LAND:
                onLandProcess();
                break;

            case TAKING_OFF:
                takingOffProcess();
                break;

            case MISSION:
                missionProcess();
                break;

            case LANDING:
                landingProcess();
                break;
        }

    }

    private void landingProcess(){
        log.info(String.format("landing , %d", time));
        double altitude = drone.getAltitude();
        if(altitude>1)
        {
            drone.setAltitude(altitude - 5);
        }
        else
            droneState = DroneState.ON_LAND;
    }

    private void takingOffProcess() {
        log.info(String.format("taking off , %d", time));
        double altitude = drone.getAltitude();
        if (altitude<20)
        {
            altitude += 5;
            drone.setAltitude(altitude);
        }
        else {
            droneState = DroneState.MISSION;
            log.info("ready to fly");
        }
    }


    private void onLandProcess(){

        switch (deliveryState){
            case TO_CLIENT:
                projector.init(home);
                computeDistance(destination);
                drone.setPosition(home);
                log.info("take off detected. going to client");
                computeSpeedProjections();
                droneState = DroneState.TAKING_OFF;
                deliveryState = DeliveryState.TO_HOME;
                break;

            case TO_HOME:
                log.info(String.format("landed on client side , %d", time));
                projector.init(drone.getPosition());
                computeDistance(home);
                log.info("take off detected. going home");
                computeSpeedProjections();
                droneState = DroneState.TAKING_OFF;
                deliveryState = DeliveryState.COMPLETED;
                break;

            case COMPLETED:
                log.info(String.format("landed at home ,  %d", time));
                deliveryCompleted = true;
                break;

            default:
                throw new IllegalArgumentException(" no such delivery state!");
        }




    }

    private void missionProcess(){
        updatePosition();
        if(missionCompleted()){
            droneState = DroneState.LANDING;
        }
    }

    private boolean missionCompleted(){
        LatLonAlt currentPosition = drone.getPosition();
        double[] currentPositionLocalCoordinates = projector.project(currentPosition);
        double distanceX = currentPositionLocalCoordinates[0] - destinationLocalCoordinates[0] ;
        double distanceY = currentPositionLocalCoordinates[1] - destinationLocalCoordinates[1];
        return 20 > sqrt(distanceX*distanceX + distanceY*distanceY);

    }


    private void updatePosition(){
        double dx = speedX*dt;
        double dy = speedY*dt;
        double[] arr = {dx, dy, 0};
        LatLonAlt newPosition  = drone.getPosition().add(arr) ;
        log.info(String.format("new position: %s , %d", newPosition, time));
        drone.setPosition(newPosition);
    }

    private void computeSpeedProjections(){
            speedX = cruiseSpeed * distanceX / distance;
            speedY = cruiseSpeed * distanceY / distance;
    }

    private void computeDistance(LatLonAlt destinationPoint){

            destinationLocalCoordinates = projector.project(destinationPoint);
            distanceX = destinationLocalCoordinates[0];
            distanceY = destinationLocalCoordinates[1];
            distance = sqrt(distanceX*distanceX + distanceY*distanceY);
            log.info(String.format("distance to fly: %.3f km", distance/1000));
    }


}
