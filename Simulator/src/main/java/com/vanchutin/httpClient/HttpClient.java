package com.vanchutin.httpClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.net.*;

@Slf4j
public class HttpClient {

    private final String producerUrl = "http://localhost:8081/producer";

    private RestTemplate restTemplate = new RestTemplate();

    public void post(String message){

        URI uri = null;
        HttpEntity<String> request = new HttpEntity<>(message);
        try {
            uri = new URI(producerUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(uri, request, String.class);
        }catch (RestClientException e){
            log.error(e.getLocalizedMessage());
            return;
        }
        if(response.getStatusCode() != HttpStatus.OK)
            log.error("Message has not been posted");
    }


}
