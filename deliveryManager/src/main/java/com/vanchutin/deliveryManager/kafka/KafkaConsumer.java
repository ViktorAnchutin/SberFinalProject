package com.vanchutin.deliveryManager.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.deliveryManager.events.DroneStateEvent;
import com.vanchutin.deliveryManager.service.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    ObjectMapper objectMapper;

    @Value(value = "${copter.topic.message}")
    private String desiredStateEvent;

    @KafkaListener(topics = "orderPlacedEvents", groupId = "deliveryManager")
    public void listenOrders(String message) {
        int orderId = Integer.valueOf(message);
        deliveryService.startDelivery(orderId);
        log.info("Received Messasge in group deliveryManager: " + message);
    }

    @KafkaListener(topics = "copterStateEvents", groupId = "deliveryManager")
    public void listenCopterState(String message) {

        DroneStateEvent droneStateEvent = null;
        try {
            droneStateEvent = objectMapper.readValue(message, DroneStateEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        if(!droneStateEvent.getEvent().equals(desiredStateEvent))
            return;

        int copterId = droneStateEvent.getDroneId();
        deliveryService.completeDelivery(copterId);
        log.info("Received drone id: " + copterId);
    }

}
