package com.vanchutin.deliveryManager.events;

import com.vanchutin.deliveryManager.model.Location;
import lombok.Data;

public @Data
class OrderPlacedEvent {
    private String order_id;
    private Location location;
}
