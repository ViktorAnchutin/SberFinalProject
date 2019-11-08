package com.vanchutin.simulation.old;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.GlobalPositionProjector;
import com.vanchutin.jmavlib.LatLonAlt;
import lombok.extern.slf4j.Slf4j;


import static java.lang.Math.sqrt;

@Slf4j
public class Mission {

    public Mission(GlobalPositionProjector projector){
        this.projector = projector;
    }

    private GlobalPositionProjector projector;
    private LatLonAlt destination;
    private final double acceptanceRadius = 20; // m
    private double distanceX, distanceY, distance;
    private double speedX, speedY, cruiseSpeed = 13.8;// m / s
    private double dt = 1;

    public double getSpeed(){
        return cruiseSpeed;
    }


    public void init(LatLonAlt start, LatLonAlt finish){
        projector.init(start);
        computeDistance(finish);
        computeSpeedProjections();
        destination = finish;
    }


    private boolean completed(LatLonAlt currentPosition){
        double[] currentPositionLocalCoordinates = projector.project(currentPosition);
        double[] destinationLocalCoordinates = projector.project(destination);
        double distanceToFinishX = currentPositionLocalCoordinates[0] - destinationLocalCoordinates[0] ;
        double distanceToFinishY = currentPositionLocalCoordinates[1] - destinationLocalCoordinates[1];
        double distanceToFinish = sqrt(distanceToFinishX*distanceToFinishX + distanceToFinishY*distanceToFinishY);
        return distanceToFinish < acceptanceRadius;
    }

    public boolean update(Drone drone){

        LatLonAlt dronePosition = drone.getPosition();
        if(completed(dronePosition))
            return true;
        else{
            LatLonAlt newPosition = updatePosition(dronePosition);
            drone.setPosition(newPosition);
        }
        return false;
    }

    private LatLonAlt updatePosition(LatLonAlt currentPosition){
        double dx = speedX*dt;
        double dy = speedY*dt;
        double[] arr = {dx, dy, 0};
       return currentPosition.add(arr) ;
    }

    private void computeSpeedProjections(){
        speedX = cruiseSpeed * distanceX / distance;
        speedY = cruiseSpeed * distanceY / distance;
    }

    private void computeDistance(LatLonAlt destinationPoint){

        double[] destinationLocalCoordinates = projector.project(destinationPoint);
        distanceX = destinationLocalCoordinates[0];
        distanceY = destinationLocalCoordinates[1];
        distance = sqrt(distanceX*distanceX + distanceY*distanceY);
        log.info(String.format("distance to fly: %.3f km", distance/1000));
    }


}
