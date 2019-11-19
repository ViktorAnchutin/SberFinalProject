package com.vanchutin.deliveryManager.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.deliveryManager.events.DroneStateEvent;
import com.vanchutin.deliveryManager.events.OrderPlacedEvent;
import com.vanchutin.deliveryManager.service.DeliveryService;
import com.vanchutin.deliveryManager.service.ServiceLayerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "spring.kafkaBeans", havingValue = "enabled")
public class KafkaConsumer {

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    ObjectMapper objectMapper;

    @Value(value = "${copter.topic.message}")
    private String desiredStateEvent;

    @KafkaListener(topics = "orderPlaced", groupId = "deliveryManager")
    public void listenOrders(String message) {
        try {
            OrderPlacedEvent eventMessage = objectMapper.readValue(message, OrderPlacedEvent.class);
            deliveryService.startDelivery(eventMessage.getOrder_id(), eventMessage.getLocation());
            log.info("Received Messasge in group deliveryManager: " + message);
        } catch (JsonProcessingException e){
            log.error("Failed to process event message", e);
        } catch (ServiceLayerException e){
            log.error("Failed to handle event", e);
        }
    }

    @KafkaListener(topics = "droneState", groupId = "deliveryManager")
    public void listenCopterState(String message) {
        try {
            DroneStateEvent droneStateEvent = null;
            droneStateEvent = objectMapper.readValue(message, DroneStateEvent.class);
            if (!droneStateEvent.getEvent().equals(desiredStateEvent))
                return;
            int copterId = droneStateEvent.getDroneId();
            deliveryService.completeDelivery(copterId);
        } catch (JsonProcessingException e){
            log.error("Failed to deserialize json", e);
        } catch (ServiceLayerException e){
            log.error("Delivery is not completed", e);
        }
    }

}
