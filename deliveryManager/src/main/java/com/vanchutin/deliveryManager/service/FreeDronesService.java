package com.vanchutin.deliveryManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.vanchutin.deliveryManager.service.restClient.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeDronesService {
    @Autowired
    HttpClientService httpClientService;


    public Integer getDroneId() throws JsonProcessingException {
        List<Integer> freeDrones =  httpClientService.getFreeDrones();
        freeDrones.forEach(System.out::println);
        return freeDrones.get(0);
    }
}
