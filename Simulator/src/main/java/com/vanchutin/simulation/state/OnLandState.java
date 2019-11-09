package com.vanchutin.simulation.state;

import com.vanchutin.simulation.Simulation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnLandState implements SimulationState {

    @Override
    public void doSimulation(Simulation simulation) {

        if( simulation.isGoingToHome() ){                   // flight is finished
            simulation.getDrone().emitLandedOnBase();
            simulation.setSimulationFinished(true);
        }
        else {
            simulation.getDrone().emitLandedAtTheClient();
            stayAtTheClient(3000);
            switchState(simulation);
        }
    }

    @Override
    public void switchState(Simulation simulation) {
              // takeoff
        simulation.setGoingToHome(true);
        simulation.setState( simulation.getTakeOffState() );
        simulation.getDrone().emitTakeOffDetected();
    }

    private void stayAtTheClient(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
