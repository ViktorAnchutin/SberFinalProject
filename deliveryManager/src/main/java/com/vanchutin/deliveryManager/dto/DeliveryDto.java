package com.vanchutin.deliveryManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
public @Data
class DeliveryDto {
    private String orderId;
    private int droneId;
}
