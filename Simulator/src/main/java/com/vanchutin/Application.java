package com.vanchutin;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.drone.Drone;
import com.vanchutin.event.Event;
import com.vanchutin.generator.EventGenerator;
import com.vanchutin.httpClient.HttpClient;
import com.vanchutin.simulation.SimulationManager;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class Application {

    public static void main(String[] args) throws Exception {
        // create drones
        Map<Integer, Drone> drones = new HashMap<>();
        for(int i=1; i<11; i++){
            drones.put(i, new Drone(i, new HttpClient()));
        }
        // create simulation manager
        SimulationManager simulationManager = new SimulationManager(drones);
        simulationManager.start();
    }
}
