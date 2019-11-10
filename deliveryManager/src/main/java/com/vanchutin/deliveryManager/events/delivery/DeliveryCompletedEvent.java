package com.vanchutin.deliveryManager.events.delivery;

public class DeliveryCompletedEvent extends DeliveryEvent {
    public DeliveryCompletedEvent(int orderId) {
        super(orderId, "delivery completed");
    }
}
