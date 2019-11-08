package com.vanchutin.simulation.state;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.AltitudeChanger;
import com.vanchutin.simulation.Simulation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LandingState extends AltitudeChanger implements SimulationState {

    @Override
    public void doSimulation(Simulation simulation) {
        Drone drone = simulation.getDrone();
        LatLonAlt currentPosition = drone.getPosition();
        if(currentPosition.alt > 1)
        {
            log.info(String.format("landing"));
            LatLonAlt newPosition = changeAlt(-5, currentPosition);
            drone.setPosition(newPosition);
            return;
        }
        switchState(simulation);
    }


    @Override
    public void switchState(Simulation simulation) {
        simulation.setState( simulation.getOnLandState() );
    }
}
