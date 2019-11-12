package com.vanchutin.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.event.StateEvent;
import com.vanchutin.event.StateEvent;

import com.vanchutin.event.TelemetryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {

    @Value(value = "${kafka.topic.state}")
    private String stateTopic;

    @Value(value = "${kafka.topic.telemetry}")
    private String telemetryTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;



    public void sendTelemetry(TelemetryEvent event){
        String msg = null;
        try {
            msg = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return;
        }

        publish(msg, telemetryTopic);
    }

    public void sendStateEvent(StateEvent event){
        String msg = null;
        try {
            msg = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return;
        }
        publish(msg, stateTopic);
    }

    private void publish(String msg, String topic) {
        kafkaTemplate.send(topic, msg);
    }



}
