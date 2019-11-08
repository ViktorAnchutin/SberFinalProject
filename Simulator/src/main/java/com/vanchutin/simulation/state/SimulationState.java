package com.vanchutin.simulation.state;

import com.vanchutin.simulation.Simulation;

public interface SimulationState {
    void doSimulation(Simulation simulation);
    void switchState(Simulation simulation);
}
