package com.vanchutin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.drone.Drone;
import com.vanchutin.models.Telemetry;
import com.vanchutin.httpClient.HttpClient;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.SimulationFactory;
import com.vanchutin.simulation.SimulationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfig {

    @Value(value = "${home.lat}")
    private double homeLat;

    @Value(value = "${home.lon}")
    private double homeLon;

    @Bean
    public SimulationManager simulationManager(){
        // create drones
        Map<Integer, Drone> drones = new HashMap<>();
        for(int i=1; i<11; i++){
            Drone drone = new Drone(i);
            drone.setTelemetry(new Telemetry());
            drone.setObjectMapper(new ObjectMapper());
            drone.setHttpClient(new HttpClient());
            drones.put(i, drone);
        }

        LatLonAlt home = new LatLonAlt(homeLat, homeLon, 0);
        SimulationManager simulationManager = new SimulationManager(drones, home);
        simulationManager.setExecutorService(Executors.newFixedThreadPool(drones.size()));
        simulationManager.setSimulationFactory( new SimulationFactory() );
        return simulationManager;
    }

}
