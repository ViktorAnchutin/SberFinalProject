package com.vanchutin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.event.EventType;
import com.vanchutin.kafka.KafkaProducer;

import com.vanchutin.service.publisherService.Publisher;
import com.vanchutin.service.publisherService.PublisherFactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    ObjectMapper objectMapper;

    Publisher publisher;

    @Autowired
    PublisherFactory publisherFactory;

    public void process(String message){
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(message);
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }
        String type = jsonNode.get("eventType").textValue();
        publisher = publisherFactory.getPublisher(type);
        publisher.process(jsonNode);
    }



}
