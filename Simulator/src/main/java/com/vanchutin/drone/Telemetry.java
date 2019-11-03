package com.vanchutin.drone;

import com.vanchutin.jmavlib.LatLonAlt;
import lombok.Data;

public @Data
class Telemetry {
    private double speed;
    private double battery;
    private LatLonAlt location;
}
