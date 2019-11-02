package com.vanchutin.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class Event {
    private String type;
    private int deviceId;
    private int componentId;
}
