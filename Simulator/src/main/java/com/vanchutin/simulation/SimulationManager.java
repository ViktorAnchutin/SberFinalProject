package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.models.Location;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class SimulationManager {

    @Setter
    private ExecutorService executorService;
    private Map<Integer, Drone> drones;
    private LatLonAlt home;
    @Setter
    private SimulationFactory simulationFactory;

    public SimulationManager(Map<Integer, Drone> drones, LatLonAlt home){
        this.drones = drones;
        this.home = home;
    }




    public synchronized boolean launchDrone(int droneId, Location clientLocation){
        Drone drone = drones.get(droneId);
        if(drone.isBusy())
            return false;
        drone.setBusy();
        executorService.execute( simulationFactory.get(drones.get(droneId), home, clientLocation));
        return true;
    }

    @PreDestroy
    public void shutDownExecutorService(){
        log.info("Shutting down executor service ...");
        executorService.shutdownNow();
    }

    public synchronized boolean hasDrone(int droneId) {
        return drones.containsKey(droneId);
    }


}
