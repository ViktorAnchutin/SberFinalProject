package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.state.LandingState;
import com.vanchutin.simulation.state.MissionState;
import com.vanchutin.simulation.state.OnLandState;
import com.vanchutin.simulation.state.TakeOffState;

public class SimulationFactory {
    public Simulation get(Drone drone, LatLonAlt home, LatLonAlt client){
        Simulation simulation = new Simulation(drone, home);
        simulation.setMissionState( new MissionState(home, client));
        simulation.setLandingState( new LandingState() );
        simulation.setOnLandState( new OnLandState() );
        simulation.setTakeOffState( new TakeOffState() );
        simulation.setState( simulation.getTakeOffState() );
        return simulation;
    }
}
