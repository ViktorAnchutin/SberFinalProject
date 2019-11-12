package com.vanchutin.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public @Data
class Telemetry {
    private double speed;
    private double battery;
    private GlobalPosition location;
}