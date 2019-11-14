package com.vanchutin.deliveryManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.vanchutin.deliveryManager.service.restClient.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreeDronesService {
    @Autowired
    HttpClientService httpClientService;


    public Integer getDroneId() throws JsonProcessingException {
        return 5;//httpClientService.getFreeDrones().get(0);
    }
}
