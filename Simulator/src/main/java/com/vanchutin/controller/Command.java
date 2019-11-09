package com.vanchutin.controller;

import com.vanchutin.jmavlib.LatLonAlt;
import lombok.Data;

public @Data
class Command {
    private int droneId;
    private LatLonAlt clientLocation;
}
