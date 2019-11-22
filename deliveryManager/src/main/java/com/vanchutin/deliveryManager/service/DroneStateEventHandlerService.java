package com.vanchutin.deliveryManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.deliveryManager.events.DroneStateEvent;
import com.vanchutin.deliveryManager.model.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DroneStateEventHandlerService {

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    ObjectMapper objectMapper;

    @Value(value = "${copter.topic.message}")
    private String desiredStateEvent;

    public void handleDroneStateEvent(String message){
        try {
            DroneStateEvent droneStateEvent = objectMapper.readValue(message, DroneStateEvent.class);
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
