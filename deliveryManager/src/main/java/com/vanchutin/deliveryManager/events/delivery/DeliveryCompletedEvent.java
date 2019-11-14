package com.vanchutin.deliveryManager.events.delivery;

public class DeliveryCompletedEvent extends DeliveryEvent {
    public DeliveryCompletedEvent(String orderId) {
        super(orderId, "delivery completed");
    }
}
