package com.vanchutin.deliveryManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.deliveryManager.events.OrderPlacedEvent;
import com.vanchutin.deliveryManager.model.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OrderHandlerService {

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FreeDronesService freeDronesService;

    public void handleOrderEvent(String eventMessage){
        log.info("Received Messasge in group deliveryManager: " + eventMessage);
        try {
            OrderPlacedEvent event = objectMapper.readValue(eventMessage, OrderPlacedEvent.class);
            // keep trying to get free available drone
            while(true){
                Optional<Integer> optionalDroneId = freeDronesService.getDroneId();
                if(optionalDroneId.isPresent()) {
                    deliveryService.startDelivery(new Delivery(event.getOrder_id(), event.getLocation(), optionalDroneId.get()));
                    break;
                } else {
                    log.info("Waiting for free drones");
                    Thread.sleep(10_000); // wait 10 sec and try to get free drone again
                }
            }
        } catch (JsonProcessingException e){
            log.error(String.format("Failed to process json event message %s", eventMessage), e);
        } catch (ServiceLayerException e){
            log.error(String.format("Failed to start delivery %s", eventMessage), e);
        } catch (InterruptedException e){
            log.error("Thread was interrupted", e);
        } catch (Exception e){
            log.error("It should have never happened...", e);
            System.exit(-1);
        }

    }
}
