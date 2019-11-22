package com.vanchutin.deliveryManager.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.deliveryManager.events.DroneStateEvent;
import com.vanchutin.deliveryManager.events.OrderPlacedEvent;
import com.vanchutin.deliveryManager.service.*;
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
    OrderHandlerService orderHandlerService;

    @Autowired
    DroneStateEventHandlerService droneStateEventHandlerService;

    @KafkaListener(topics = "orderPlaced", groupId = "deliveryManager", properties= {"max.poll.interval.ms:" + Integer.MAX_VALUE})
    public void listenOrders(String message) {
        orderHandlerService.handleOrderEvent(message);
        log.info("Returning from the listener");
    }

    @KafkaListener(topics = "droneState", groupId = "deliveryManager")
    public void listenCopterState(String message) {
        droneStateEventHandlerService.handleDroneStateEvent(message);
    }

}
