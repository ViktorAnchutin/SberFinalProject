package com.vanchutin.deliveryManager.events.delivery;

public class CopterSentEvent extends DeliveryEvent {
    public CopterSentEvent(String orderId) {
        super(orderId, "copter sent");
    }
}
