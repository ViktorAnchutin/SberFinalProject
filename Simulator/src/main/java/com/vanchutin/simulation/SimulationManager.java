package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimulationManager {

    private Set<Integer> availableDrones;
    private Map<Integer, Drone> drones;

    public SimulationManager(Map<Integer, Drone> drones){
        this.drones = drones;
        availableDrones = new HashSet<>();
        // set all drones available at first
        drones.forEach((id, drone) -> availableDrones.add(id));
    }

    public void start(){
        // launch one delivery
        FlightSimulation simulation = new FlightSimulation(drones.get(1), new LatLonAlt(55.980261, 37.899619, 0), new LatLonAlt(56, 37.899619, 0));
        Thread copterOne = new Thread(simulation);
        copterOne.setName("thread copter 1");
        copterOne.start();

        FlightSimulation simulation1 = new FlightSimulation(drones.get(1), new LatLonAlt(55.980261, 37.899619, 0), new LatLonAlt(56, 37.899619, 0));
        Thread copterTwo = new Thread(simulation1);
        copterTwo.setName(" thread copter 2");
        copterTwo.start();

    }

}
