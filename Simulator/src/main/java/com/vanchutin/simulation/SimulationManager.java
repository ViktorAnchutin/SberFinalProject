package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
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

    public void start(){
         launchDrone(1, new LatLonAlt(55.982099, 37.909270, 0));
         executorService.shutdown();
    }


    public synchronized boolean launchDrone(int droneId, LatLonAlt client){
        Drone drone = drones.get(droneId);
        if(drone.isBusy())
            return false;
        drone.setBusy();
        executorService.execute( simulationFactory.get(drones.get(droneId), home, client));
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
