package com.vanchutin.deliveryManager.events.delivery;

public class CopterSentEvent extends DeliveryEvent {
    public CopterSentEvent(int orderId) {
        super(orderId, "copter sent");
    }
}
