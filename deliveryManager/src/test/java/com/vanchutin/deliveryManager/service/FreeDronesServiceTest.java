package com.vanchutin.deliveryManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest(properties = "spring.kafkaBeans=disabled")
@RunWith(SpringRunner.class)
public class FreeDronesServiceTest {

    @Autowired
    FreeDronesService freeDronesService;

    @Test
    public void getDroneId() throws ServiceLayerException {
           int res = freeDronesService.getDroneId();

    }
}