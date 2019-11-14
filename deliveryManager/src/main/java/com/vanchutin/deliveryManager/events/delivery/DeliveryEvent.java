package com.vanchutin.deliveryManager.events.delivery;

import lombok.Data;

public @Data abstract class DeliveryEvent {
    protected String order_id;
    protected String event;
    public DeliveryEvent(String orderId, String event){
        this.order_id = orderId;
        this.event = event;
    }
}
