package com.vanchutin.deliveryManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vanchutin.deliveryManager.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest(properties = "spring.kafkaBeans=disabled")
@RunWith(SpringRunner.class)
public class SimulatorManagerServiceTest {

    @Autowired
    SimulatorManagerService simulationService;

    @Test
    public void launchDrone_shouldLaunchDrone() {
        try {
            simulationService.launchDrone(3, new Location(55.982099, 37.909270));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}