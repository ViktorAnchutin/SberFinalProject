package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.state.*;
import lombok.Getter;
import lombok.Setter;


public class Simulation implements Runnable{

    @Getter @Setter
    private OnLandState onLandState;
    @Getter @Setter
    private TakeOffState takeOffState;
    @Getter @Setter
    private MissionState missionState;
    @Getter @Setter
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


    public Simulation(Drone drone, LatLonAlt home){
        this.drone = drone;
        this.home = home;
    }


    @Override
    public void run() {

        drone.setPosition(home);
        drone.setBattery(100);

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
