package com.vanchutin.drone;

import com.vanchutin.httpClient.HttpClient;
import com.vanchutin.jmavlib.LatLonAlt;
import lombok.Data;

public @Data class Drone {

    private int id;
    private int speed;
    private double altitude;
    LatLonAlt position;
    private float battery;
    HttpClient httpClient;


    public Drone(int id, HttpClient httpClient){
        this.id = id;
        this.httpClient = httpClient;
    }

    public void sendTelemetry(){

    }

    public void sendEvent(){

    }

}
