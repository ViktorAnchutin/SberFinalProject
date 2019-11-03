package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Slf4j
public class SimulationManager {

    private Set<Integer> flyingDrones;
    private Map<Integer, Drone> drones;
    private LatLonAlt home;

    public SimulationManager(Map<Integer, Drone> drones, LatLonAlt home){
        this.drones = drones;
        this.home = home;
        flyingDrones = new HashSet<>();
    }

    public void start(){

        int droneId=1;

        while(true) {
            if (flyingDrones.size() < 5) {
                launchDrone(droneId++, new LatLonAlt(55.982099, 37.909270, 0));
                if(droneId > drones.size())
                    droneId = 1;
            }
            sleep(100);
        }
    }

    private void launchDrone(int droneId, LatLonAlt client){
        flyingDrones.add(droneId);
        FlightSimulation simulation = new FlightSimulation(drones.get(droneId), home, client);
        simulation.setCallback(this::setAvailable);
        Thread thread = new Thread(simulation);
        thread.setName(String.format(" thread drone %d", droneId));
        thread.start();
    }

    private synchronized void setAvailable(Drone drone){
        log.info(String.format("Drone %d is available.", drone.getId()));
        flyingDrones.remove(drone.getId());
    }

    private void sleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
