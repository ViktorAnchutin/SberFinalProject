package com.vanchutin.deliveryManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data
class Delivery {
    private String orderId;
    private Location clientLocation;
    private int droneId;
}
