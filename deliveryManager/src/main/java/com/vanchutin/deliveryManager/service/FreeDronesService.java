package com.vanchutin.deliveryManager.service;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreeDronesService {

    @Autowired
    HttpClientService httpClientService;

    private int var;

    public Optional<Integer> getDroneId() throws ServiceLayerException{
        var++;
        if(var>2){
            if(var>4){
                return Optional.of(var);
            }
            return Optional.empty();
        }
        return Optional.of(var);

       /* return Optional.of(var);
        List<Integer> freeDrones =  httpClientService.getFreeDrones();
        if(freeDrones.isEmpty())
            return Optional.empty();
        return Optional.of(freeDrones.get(0)); */

    }
}
