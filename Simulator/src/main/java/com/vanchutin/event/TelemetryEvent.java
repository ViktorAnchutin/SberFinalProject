package com.vanchutin.event;

import com.vanchutin.drone.Telemetry;
import lombok.Data;

public @Data
class TelemetryEvent extends Event{

    public TelemetryEvent(int id, Telemetry telemetry) {
        super(id, EventType.TELEMETRY);
        this.telemetry = telemetry;
    }

    private  Telemetry telemetry;
}
