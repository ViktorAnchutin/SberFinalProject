package com.vanchutin.drone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.event.Event;
import com.vanchutin.event.StateEvent;
import com.vanchutin.event.TelemetryEvent;
import com.vanchutin.httpClient.HttpClient;
import com.vanchutin.jmavlib.LatLonAlt;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public @Data class Drone {

    int id;
    Telemetry telemetry;
    HttpClient httpClient;
    ObjectMapper objectMapper;


    public Drone(int id, HttpClient httpClient){
        this.id = id;
        this.httpClient = httpClient;
        objectMapper = new ObjectMapper();
        telemetry = new Telemetry();
    }

    public void sendTelemetry(){
        TelemetryEvent telemetryEvent = new TelemetryEvent(id, telemetry);
        sendEvent(telemetryEvent);
    }

    public void sendMessage(String message){
        StateEvent event = new StateEvent(id, message);
        sendEvent(event);
    }

    public void setBattery(double battery){
        telemetry.setBattery(battery);
    }

    public void setSpeed(double speed){
        telemetry.setSpeed(speed);
    }




    private void sendEvent(Event event){
        try {
            String message =  objectMapper.writeValueAsString(event);
            System.out.println(message);
            httpClient.post(message);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public LatLonAlt getPosition() {
        return telemetry.getLocation();
    }

    public void setPosition(LatLonAlt newPosition) {
        telemetry.setLocation(newPosition);
    }

    public void updateBattery() {
        double newBattery = telemetry.getBattery() - 0.3;
        if(newBattery<0) newBattery = 0;
        telemetry.setBattery(newBattery);
    }
}
