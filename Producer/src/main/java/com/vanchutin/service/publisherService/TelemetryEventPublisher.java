package com.vanchutin.service.publisherService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vanchutin.event.StateEvent;
import com.vanchutin.event.TelemetryEvent;
import com.vanchutin.models.Telemetry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelemetryEventPublisher extends Publisher {
    @Override
    public void process(JsonNode jsonNode) {
        int droneId = jsonNode.get("id").asInt();
        JsonNode telemetryNode = jsonNode.get("telemetry");
        Telemetry telemetry=null;
        try {
            telemetry = objectMapper.treeToValue(telemetryNode, Telemetry.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return;
        }
        kafkaProducer.sendTelemetry(new TelemetryEvent(droneId, telemetry));

    }
}
