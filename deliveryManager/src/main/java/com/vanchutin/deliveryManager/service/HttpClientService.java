package com.vanchutin.deliveryManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.deliveryManager.service.ServiceLayerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class HttpClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${stateService.freeDrones.url}")
    private String freeDronesURL;

    @Autowired
    ObjectMapper objectMapper;

    @Value(value = "${simulator.url}")
    String simulatorUrl;

    public void post(String message) throws ServiceLayerException {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(message, headers);
            URI uri = new URI(simulatorUrl);
            ResponseEntity<String> response = null;
            response = restTemplate.postForEntity(uri, request, String.class);
        } catch (URISyntaxException e){
            throw new ServiceLayerException(String.format("Failed to parse URL for post request %s", simulatorUrl), e);
        } catch (RestClientException e){
            throw new ServiceLayerException(String.format("Post request failed. Could not launch a drone with a command %s", message), e);
        }


    }

    public List<Integer> getFreeDrones() throws ServiceLayerException {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(freeDronesURL, String.class);
            Integer[] dronesArr = objectMapper.readValue(response.getBody(), Integer[].class);
            return Arrays.asList(dronesArr);
        } catch (JsonProcessingException e){
            throw new ServiceLayerException("Failed to process json", e);
        } catch (RestClientException e){
            throw new ServiceLayerException("Failed to get free drones", e);
        }
    }
}
