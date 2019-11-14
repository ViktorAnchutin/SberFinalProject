package com.vanchutin.deliveryManager.service;

import com.vanchutin.deliveryManager.dao.DeliveryDao;
import com.vanchutin.deliveryManager.events.delivery.CopterSentEvent;
import com.vanchutin.deliveryManager.events.delivery.DeliveryCompletedEvent;

import com.vanchutin.deliveryManager.kafka.KafkaProducer;
import com.vanchutin.deliveryManager.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
public class DeliveryService {

    @Autowired
    DeliveryDao deliveryDao;

    @Autowired(required = false)
    KafkaProducer kafkaProducer;

    @Autowired
    FreeDronesService freeDronesService;

    @Autowired
    SimulatorManagerService simulatorManager;



    public void startDelivery(String orderId, Location clientLocation){
        try {
            int droneId = freeDronesService.getDroneId();
            simulatorManager.launchDrone(droneId, clientLocation);
            deliveryDao.createDelivery(droneId, orderId);
            kafkaProducer.publishEvent(new CopterSentEvent(orderId));
        }
        catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public void completeDelivery(int droneId){
        String orderId = deliveryDao.getOrderByDroneId(droneId);
        deliveryDao.deleteDelivery(droneId);
        kafkaProducer.publishEvent(new DeliveryCompletedEvent(orderId));
    }

}
