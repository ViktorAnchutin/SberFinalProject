package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
public class SimulationManager {

    private Map<Integer, Drone> drones;
    private LatLonAlt home;

    public SimulationManager(Map<Integer, Drone> drones, LatLonAlt home){
        this.drones = drones;
        this.home = home;
    }

    public void start(){
         launchDrone(1, new LatLonAlt(55.982099, 37.909270, 0));
    }

    private void launchDrone(int droneId, LatLonAlt client){
        Simulation simulation = new Simulation(drones.get(droneId), home, client);
        Thread thread = new Thread(simulation);
        thread.setName(String.format(" thread drone %d", droneId));
        thread.start();
    }
}
