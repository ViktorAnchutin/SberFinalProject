package com.vanchutin.producer;


import com.vanchutin.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProducer{

    @Autowired
    KafkaProducer kafkaProducer;

    public void publish(String message){
           kafkaProducer.sendMessage(message);
    }

}
