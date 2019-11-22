package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("copters")
public class TestController {


    @GetMapping(path = "freeOnes")
    public ResponseEntity<Integer[]> getFreeCopters(){
        Integer[] freeCopters = new Integer[0];
        return ResponseEntity.ok(freeCopters);
    }

}
