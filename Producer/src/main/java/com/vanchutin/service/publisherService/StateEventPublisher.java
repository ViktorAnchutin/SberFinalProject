package com.vanchutin.service.publisherService;

import com.fasterxml.jackson.databind.JsonNode;
import com.vanchutin.event.StateEvent;
import org.springframework.stereotype.Component;

@Component
public class StateEventPublisher extends Publisher {
    @Override
    public void process(JsonNode jsonNode) {
        int droneId = jsonNode.get("id").asInt();
        String event = jsonNode.get("message").asText();
        kafkaProducer.sendStateEvent(new StateEvent(droneId, event));
    }
}
