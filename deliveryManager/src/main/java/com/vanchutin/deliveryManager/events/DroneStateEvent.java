package com.vanchutin.deliveryManager.events;

import lombok.Data;

public @Data
class DroneStateEvent {
    private int droneId;
    private String event;
}
