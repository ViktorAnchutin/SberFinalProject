package com.vanchutin.deliveryManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class FreeDronesService {
    @Autowired
    HttpClientService httpClientService;

    static int i=0;

    public Integer getDroneId(){

        return i++;
    }
}
