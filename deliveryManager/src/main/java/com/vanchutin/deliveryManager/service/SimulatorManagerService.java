package com.vanchutin.deliveryManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.deliveryManager.model.Command;
import com.vanchutin.deliveryManager.model.Location;
import com.vanchutin.deliveryManager.service.restClient.HttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
@Slf4j
public class SimulatorManagerService {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value(value = "${simulator.url}")
    String simulatorUrl;


    public void launchDrone(int droneId, Location clientLocation) throws ServiceLayerException {
        try {
            Command command = new Command(droneId, clientLocation);
            String jsonMsg = objectMapper.writeValueAsString(command);
            httpClientService.post(jsonMsg, simulatorUrl);
        } catch (JsonProcessingException e){
            throw new ServiceLayerException("Failed processing json", e);
        }
    }
}
