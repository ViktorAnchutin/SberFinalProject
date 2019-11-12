package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.models.Location;
import com.vanchutin.simulation.state.LandingState;
import com.vanchutin.simulation.state.MissionState;
import com.vanchutin.simulation.state.OnLandState;
import com.vanchutin.simulation.state.TakeOffState;

public class SimulationFactory {
    public Simulation get(Drone drone, LatLonAlt home, Location clientLocation){
        Simulation simulation = new Simulation(drone, home);
        simulation.setMissionState( new MissionState(home, new LatLonAlt(clientLocation.getLat(), clientLocation.getLon(), 0)));
        simulation.setLandingState( new LandingState() );
        simulation.setOnLandState( new OnLandState() );
        simulation.setTakeOffState( new TakeOffState() );
        simulation.setState( simulation.getTakeOffState() );
        return simulation;
    }
}
