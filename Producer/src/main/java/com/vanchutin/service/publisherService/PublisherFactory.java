package com.vanchutin.service.publisherService;

import com.vanchutin.event.EventType;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PublisherFactory {

    @Autowired
    ApplicationContext applicationContext;

    public Publisher getPublisher(String eventType){
        if(eventType.equals(EventType.DRONE_STATE)){
            return applicationContext.getBean(StateEventPublisher.class);
        }
        else if(eventType.equals(EventType.DRONE_TELEMETRY)){
            return applicationContext.getBean(TelemetryEventPublisher.class);
        }
        else{
            throw new IllegalArgumentException(String.format("No such event: %s", eventType));
        }
    }
}
