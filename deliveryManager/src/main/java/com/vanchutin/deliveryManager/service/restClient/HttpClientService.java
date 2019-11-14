package com.vanchutin.deliveryManager.service.restClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
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

    public void post(String message, String url) throws URISyntaxException {


         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         HttpEntity<String> request = new HttpEntity<>(message, headers);
         URI uri = new URI(url);
         ResponseEntity<String> response = null;
         response = restTemplate.postForEntity(uri, request, String.class);

    }

    public List<Integer> getFreeDrones() throws JsonProcessingException {

        ResponseEntity<String> response = restTemplate.getForEntity(freeDronesURL, String.class);
        Integer[] dronesArr = objectMapper.readValue(response.getBody(), Integer[].class);
        return Arrays.asList(dronesArr);
    }
}
