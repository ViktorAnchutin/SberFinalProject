package com.vanchutin.event;

import com.vanchutin.models.Telemetry;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
public @Data
class TelemetryEvent {
    private int id;
    private Telemetry telemetry;
}
