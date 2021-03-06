package com.vanchutin.deliveryManager.service;

import com.vanchutin.deliveryManager.dao.DaoLayerException;
import com.vanchutin.deliveryManager.dao.DeliveryDao;
import com.vanchutin.deliveryManager.dto.DeliveryDto;
import com.vanchutin.deliveryManager.events.delivery.CopterSentEvent;
import com.vanchutin.deliveryManager.events.delivery.DeliveryCompletedEvent;

import com.vanchutin.deliveryManager.kafka.KafkaProducer;
import com.vanchutin.deliveryManager.model.Delivery;
import com.vanchutin.deliveryManager.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
@Transactional
public class DeliveryService {

    @Autowired
    DeliveryDao deliveryDao;

    @Autowired(required = false) // not inject the bean if kafka beans are disabled
    KafkaProducer kafkaProducer;

    @Autowired
    SimulatorManagerService simulatorManager;



    public void startDelivery(Delivery delivery) throws ServiceLayerException {
        try {
            simulatorManager.launchDrone(delivery.getDroneId(), delivery.getClientLocation());
            deliveryDao.createDelivery(delivery.getDroneId(), delivery.getOrderId());
            kafkaProducer.publishEvent(new CopterSentEvent(delivery.getOrderId()));
        }
        catch (DaoLayerException e){
            throw new ServiceLayerException("Failed to create delivery", e);
        }
    }

    public void completeDelivery(int droneId) throws ServiceLayerException {
        try {
            String orderId = deliveryDao.getOrderByDroneId(droneId);
            deliveryDao.deleteDelivery(droneId);
            kafkaProducer.publishEvent(new DeliveryCompletedEvent(orderId));
        }catch (DaoLayerException e){
            throw new ServiceLayerException("Failed to complete delivery", e);
        }
    }

    public Optional<DeliveryDto> getDeliveryByOrder(String orderId) throws ServiceLayerException {
        try {
            Optional<Integer> droneId = deliveryDao.getDroneByOrderId(orderId);
            return droneId.map(integer -> new DeliveryDto(orderId, integer));
        }catch (DaoLayerException e){
            throw new ServiceLayerException("Service failed to get droneId for orderId", e);
        }
    }

}
