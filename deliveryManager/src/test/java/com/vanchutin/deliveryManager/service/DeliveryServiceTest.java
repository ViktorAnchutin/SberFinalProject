package com.vanchutin.deliveryManager.service;

import com.vanchutin.deliveryManager.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest(properties = "spring.kafkaBeans=disabled")
@RunWith(SpringRunner.class)
public class DeliveryServiceTest {

    @Autowired
    DeliveryService deliveryService;

    @Test
    public void startDelivery() {
      //  deliveryService.startDelivery(3, new Location(4,4));
    }

    @Test
    public void completeDelivery() {
    }
}