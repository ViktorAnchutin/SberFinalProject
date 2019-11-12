package com.vanchutin.event;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
public @Data
class StateEvent {
    private int droneId;
    private String event;
}
