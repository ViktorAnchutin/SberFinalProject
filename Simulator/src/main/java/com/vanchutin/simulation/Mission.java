package com.vanchutin.simulation;

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
    private double[] destinationLocalCoordinates;
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
    }


    public boolean completed(LatLonAlt currentPosition){
        double[] currentPositionLocalCoordinates = projector.project(currentPosition);
        double distanceX = currentPositionLocalCoordinates[0] - destinationLocalCoordinates[0] ;
        double distanceY = currentPositionLocalCoordinates[1] - destinationLocalCoordinates[1];
        return acceptanceRadius > sqrt(distanceX*distanceX + distanceY*distanceY);
    }

    public LatLonAlt updatePosition(LatLonAlt currentPosition){
        double dx = speedX*dt;
        double dy = speedY*dt;
        double[] arr = {dx, dy, 0};
       return currentPosition.add(arr) ;
    }

    public LatLonAlt changeAlt(int val, LatLonAlt currentPosition){
        double[] changeVector = new double[]{0,0,val};
        return currentPosition.add(changeVector);
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
