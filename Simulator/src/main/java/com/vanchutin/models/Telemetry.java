package com.vanchutin.models;

import com.vanchutin.jmavlib.LatLonAlt;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public @Data
class Telemetry {
    private double speed;
    private double battery = 100;
    private LatLonAlt location;
}
