package com.vanchutin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
public @Data class Command {
    private int droneId;
    private Location clientLocation;
}
