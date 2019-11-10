package com.vanchutin.deliveryManager.kafka;

import com.vanchutin.deliveryManager.events.delivery.CopterSentEvent;
import com.vanchutin.deliveryManager.events.delivery.DeliveryCompletedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaProducerTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Test
    public void publishEvent_shouldPublishEvent() {
        kafkaProducer.publishEvent(new DeliveryCompletedEvent(34));
        kafkaProducer.publishEvent(new CopterSentEvent(65));
    }
}