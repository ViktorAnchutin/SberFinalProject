package com.vanchutin.deliveryManager.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest(properties = "spring.kafkaBeans=disabled")
@RunWith(SpringRunner.class)
public class DeliveryDaoTest {

    @Autowired
    DeliveryDao deliveryDao;

    @Test
    public void createDelivery_shouldCreateRow() {
       // deliveryDao.createDelivery(63,88);
    }

    @Test
    public void getOrderByDroneId_shouldReturnOrderID() {
      /*  int orderId = deliveryDao.getOrderByDroneId(57);
        assertEquals(34, orderId); */
    }
}