package com.vanchutin.deliveryManager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Slf4j
public class HttpClientService {

    @Autowired
    private RestTemplate restTemplate;

    public void post(String message, String url) {

     try {
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         HttpEntity<String> request = new HttpEntity<>(message, headers);
         URI uri = new URI(url);
         ResponseEntity<String> response = null;
         response = restTemplate.postForEntity(uri, request, String.class);
        }
     catch (URISyntaxException e){
         log.error(e.getMessage());
         throw new RestClientException("Failed parsing URL");
        }
    }
}
