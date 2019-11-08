package com.vanchutin.simulation.state;


import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.AltitudeChanger;
import com.vanchutin.simulation.Simulation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TakeOffState extends AltitudeChanger implements SimulationState {

    private final int UPDATE_STEP = 5; // m
    private final int DESIRED_ALT = 20; // m

    @Override
    public void doSimulation(Simulation simulation) {

        Drone drone = simulation.getDrone();
        LatLonAlt currentPosition = drone.getPosition();
        if (currentPosition.alt < DESIRED_ALT)
        {
            log.info(String.format("taking off"));
            LatLonAlt newPosition = changeAlt(UPDATE_STEP, currentPosition);
            drone.setPosition(newPosition);
            return;
        }
        switchState(simulation);
    }


    @Override
    public void switchState(Simulation simulation) {
        if(simulation.isGoingToHome())
            simulation.setState( simulation.getMissionState().goHome() );
        else
            simulation.setState( simulation.getMissionState() );

        simulation.getDrone().emitMissionStarted();
    }
}
