package com.vanchutin.simulation.state;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.GlobalPositionProjector;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.Simulation;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.lang.Math.sqrt;

@Slf4j
@NoArgsConstructor
@Component
public class MissionState implements SimulationState {

    private GlobalPositionProjector projector;
    private LatLonAlt start;
    private LatLonAlt finish;
    private final double acceptanceRadius = 20; // m
    private double distanceX, distanceY, distance;
    private double speedX, speedY, cruiseSpeed = 13.8;// m / s
    private int dt = 1;

    public MissionState(LatLonAlt start, LatLonAlt finish){
        this.start = start;
        this.finish = finish;
        projector = new GlobalPositionProjector();
        init(start, finish);
    }

    @Override
    public void doSimulation(Simulation simulation) {
        Drone drone = simulation.getDrone();
        drone.setSpeed(cruiseSpeed);
        LatLonAlt dronePosition = drone.getPosition();
        LatLonAlt newPosition = updatePosition(dronePosition);
        drone.setPosition(newPosition);
        if(finished(dronePosition)) {
            switchState(simulation);
        }
    }

    @Override
    public void switchState(Simulation simulation) {
        Drone drone = simulation.getDrone();
        drone.setSpeed(0);
        simulation.setState(simulation.getLandingState());
        drone.emitLanding();
    }

    public void init(LatLonAlt start, LatLonAlt finish){
        this.start = start;
        this.finish = finish;
        projector.init(start);
        computeDistance(finish);
        computeSpeedProjections();
    }

    public MissionState goHome(){
        init(finish, start); // change direction
        return this;
    }

    private LatLonAlt updatePosition(LatLonAlt currentPosition){
        double dx = speedX*dt;
        double dy = speedY*dt;
        double[] arr = {dx, dy, 0};
        return currentPosition.add(arr) ;
    }



    private boolean finished(LatLonAlt currentPosition){
        double[] currentPositionLocalCoordinates = projector.project(currentPosition);
        double[] destinationLocalCoordinates = projector.project(finish);
        double distanceToFinishX = currentPositionLocalCoordinates[0] - destinationLocalCoordinates[0] ;
        double distanceToFinishY = currentPositionLocalCoordinates[1] - destinationLocalCoordinates[1];
        double distanceToFinish = sqrt(distanceToFinishX*distanceToFinishX + distanceToFinishY*distanceToFinishY);
        return distanceToFinish < acceptanceRadius;
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
