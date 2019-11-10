package com.vanchutin.deliveryManager.events;

public class DeliveryCompletedEvent extends DeliveryEvent {
    public DeliveryCompletedEvent(int orderId) {
        super(orderId, "delivery completed");
    }
}
