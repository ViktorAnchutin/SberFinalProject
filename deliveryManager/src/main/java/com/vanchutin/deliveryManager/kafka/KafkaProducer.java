package com.vanchutin.deliveryManager.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.deliveryManager.events.delivery.DeliveryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {

    @Value(value = "${delivery.topic.name}")
    String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void publishEvent(DeliveryEvent event){
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic,message);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }



}
