package com.vanchutin.controller;

import com.vanchutin.producer.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("producer")
@Slf4j
public class ProducerController {

    @Autowired
    MessageProducer messageProducer;

    @PostMapping
    public ResponseEntity<String> createEvent(HttpEntity<String> httpEntity){
        String message  =  httpEntity.getBody();
        log.info(String.format("Agent message received: %s", message));
        messageProducer.publish(message);
        return ResponseEntity.ok().build();
    }

}
