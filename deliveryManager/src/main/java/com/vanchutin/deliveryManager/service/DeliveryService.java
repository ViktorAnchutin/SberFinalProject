package com.vanchutin.deliveryManager.service;

import com.vanchutin.deliveryManager.dao.DeliveryDao;
import com.vanchutin.deliveryManager.events.delivery.CopterSentEvent;
import com.vanchutin.deliveryManager.events.delivery.DeliveryCompletedEvent;
import com.vanchutin.deliveryManager.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryService {

    @Autowired
    DeliveryDao deliveryDao;

    @Autowired
    KafkaProducer kafkaProducer;

    public void startDelivery(int orderId){
        int droneId=0;
        // http client. get request [HttpClientService]
        // pick the drone
        // http client. sent command to simulator [HttpClientService]
        deliveryDao.createDelivery(droneId, orderId);
        kafkaProducer.publishEvent(new CopterSentEvent(orderId));
    }

    public void completeDelivery(int droneId){
        int orderId = deliveryDao.getOrderByDroneId(droneId);
        kafkaProducer.publishEvent(new DeliveryCompletedEvent(orderId));
    }

}
