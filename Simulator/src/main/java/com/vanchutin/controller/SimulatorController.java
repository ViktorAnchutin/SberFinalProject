package com.vanchutin.controller;

import com.vanchutin.simulation.SimulationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SimulatorController {

    @Autowired
    SimulationManager simulationManager;

    @PostMapping
    public ResponseEntity launchDrone(@RequestBody Command command) {

        int droneId = command.getDroneId();

        if(!simulationManager.hasDrone(droneId))
            return  ResponseEntity.badRequest().body(String.format("Drone with id = %d does not exist", droneId));

        if(simulationManager.launchDrone(droneId, command.getClientLocation())){
            return ResponseEntity.ok().body(command);
        }

        return  ResponseEntity.badRequest().body(String.format("Drone with id = %d is busy", droneId));
    }

}
