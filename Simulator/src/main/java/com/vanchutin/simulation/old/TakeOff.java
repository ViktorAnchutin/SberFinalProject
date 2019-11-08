package com.vanchutin.simulation.old;

import com.vanchutin.drone.Drone;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.simulation.AltitudeChanger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TakeOff extends AltitudeChanger {

    private final int UPDATE_STEP = 5; // m
    private final int DESIRED_ALT = 20; // m


    public boolean update(Drone drone){

        LatLonAlt currentPosition = drone.getPosition();
        if (currentPosition.alt < DESIRED_ALT)
        {
            log.info(String.format("taking off"));
            LatLonAlt newPosition = changeAlt(UPDATE_STEP, currentPosition);
            drone.setPosition(newPosition);
            return false;
        }
        return true;
    }

}
