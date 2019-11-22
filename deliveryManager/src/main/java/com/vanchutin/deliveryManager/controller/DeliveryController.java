package com.vanchutin.deliveryManager.controller;

import com.vanchutin.deliveryManager.dto.DeliveryDto;

import com.vanchutin.deliveryManager.service.DeliveryService;
import com.vanchutin.deliveryManager.service.ServiceLayerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping(value = "delivery")
@Slf4j
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

        @GetMapping
        public ResponseEntity getDelivery(@RequestParam(name = "order") String orderId){
            try {
                Optional<DeliveryDto> delivery = deliveryService.getDeliveryByOrder(orderId);
                return ResponseEntity.ok(delivery.orElse(new DeliveryDto(orderId, 0))); // return droneId 0 if there is no drone assigned for a given order
            } catch (ServiceLayerException e){
                log.error(e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was a problem in Getting delivery information");
            }
        }

}
