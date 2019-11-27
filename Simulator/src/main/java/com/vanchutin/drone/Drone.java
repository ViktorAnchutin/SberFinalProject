package com.vanchutin.drone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanchutin.event.Event;
import com.vanchutin.event.StateEvent;
import com.vanchutin.event.TelemetryEvent;
import com.vanchutin.httpClient.HttpClient;
import com.vanchutin.jmavlib.LatLonAlt;
import com.vanchutin.models.Telemetry;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public @Data class Drone {

    private int id;
    @Setter
    private Telemetry telemetry;
    @Setter
    HttpClient httpClient;
    @Setter
    private ObjectMapper objectMapper;

    private boolean busy = false;

    public Drone(int id){
        this.id = id;
    }

    public void sendTelemetry(){
        TelemetryEvent telemetryEvent = new TelemetryEvent(id, telemetry);
        sendEvent(telemetryEvent);
    }

    public void emitLandedAtTheClient(){
        sendMessage("landed at the client");
    }

    public void emitLandedOnBase(){
        sendMessage("landed on base");
    }

    public void emitMissionStarted(){
        sendMessage("mission started");
    }

    public void emitLanding(){
        sendMessage("landing");
    }

    public void emitTakeOffDetected(){
        sendMessage("take off detected");
    }


    public void setBattery(double battery){
        telemetry.setBattery(battery);
    }

    public void setSpeed(double speed){
        telemetry.setSpeed(speed);
    }



    private void sendMessage(String message){
        StateEvent event = new StateEvent(id, message);
        sendEvent(event);
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
        double newBattery = telemetry.getBattery() - 0.05;
        if(newBattery<0) newBattery = 0;
        telemetry.setBattery(newBattery);
    }

    public synchronized boolean isBusy() {
        return this.busy;
    }

    public synchronized void setBusy(){
        this.busy = true;
    }

    public synchronized void setFree(){
        this.busy = false;
    }

}
