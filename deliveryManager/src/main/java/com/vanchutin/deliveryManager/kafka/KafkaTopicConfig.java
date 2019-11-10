package com.vanchutin.deliveryManager.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${copter.topic.name}")
    private String copterStateTopic;

    @Value(value = "${delivery.topic.name}")
    private String deliveryTopic;

    @Value(value = "${order.topic.name}")
    private String orderPlacedTopic;


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic deliveryTopic() {
        return new NewTopic(deliveryTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic orderPlacedTopic() {
        return new NewTopic(orderPlacedTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic copterStateTopic() {
        return new NewTopic(copterStateTopic, 1, (short) 1);
    }
}