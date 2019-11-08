package com.vanchutin.simulation.old;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.AltitudeChanger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Landing extends AltitudeChanger {

    public boolean update(Drone drone){

        LatLonAlt currentPosition = drone.getPosition();
        if(currentPosition.alt > 1)
        {
            log.info(String.format("landing"));
            LatLonAlt newPosition = changeAlt(-5, currentPosition);
            drone.setPosition(newPosition);
            return false;
        }
        return true;
    }
}
