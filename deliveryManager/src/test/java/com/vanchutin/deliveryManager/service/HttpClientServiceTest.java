package com.vanchutin.deliveryManager.service;

import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest(properties = "spring.kafkaBeans=disabled")
@RunWith(SpringRunner.class)
public class HttpClientServiceTest {

    @Autowired
    HttpClientService httpClientService;

    @Test
    public void post() {
    }

    @Test
    public void getFreeDrones() throws ServiceLayerException {
        List<Integer> drones = httpClientService.getFreeDrones();
        System.out.println(String.format("Size: %d",drones.size()));
        drones.forEach(System.out::println);
    }
}