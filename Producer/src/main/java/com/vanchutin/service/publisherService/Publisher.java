package com.vanchutin.service.publisherService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public abstract class Publisher {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    protected KafkaProducer kafkaProducer;

    public abstract void process(JsonNode jsonNode);
}
