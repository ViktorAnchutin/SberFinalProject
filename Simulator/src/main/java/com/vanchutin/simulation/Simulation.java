package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.state.*;
import lombok.Getter;
import lombok.Setter;

public class Simulation implements Runnable{

    @Getter
    private OnLandState onLandState;
    @Getter
    private TakeOffState takeOffState;
    @Getter
    private MissionState missionState;
    @Getter
    private LandingState landingState;
    @Setter
    private SimulationState state;

    @Getter
    private Drone drone;
    private LatLonAlt home;
    private LatLonAlt destination;
    @Getter @Setter
    private boolean goingToHome = false;
    @Getter @Setter
    private boolean simulationFinished = false;


    public Simulation(Drone drone, LatLonAlt home, LatLonAlt destination){
        this.drone = drone;
        this.destination = destination;
        this.home = home;
        onLandState = new OnLandState();
        takeOffState = new TakeOffState();
        missionState = new MissionState(home, destination);
        landingState = new LandingState();
        state = takeOffState;
    }


    @Override
    public void run() {

        drone.setPosition(home);

        while ( !simulationFinished ) {
            state.doSimulation(this);
            drone.updateBattery();
            drone.sendTelemetry();
            sleep(1000);
        }
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
