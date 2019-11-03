package com.vanchutin.simulation;

import com.vanchutin.drone.Drone;
import com.vanchutin.drone.DroneState;
import com.vanchutin.jmavlib.GlobalPositionProjector;
import com.vanchutin.jmavlib.LatLonAlt;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class FlightSimulation implements Runnable {

    private LatLonAlt home;
    private LatLonAlt client;
    private Mission mission;
    private Drone drone;

    private ThreadCallback<Drone> callback;

    private DroneState droneState = DroneState.ON_LAND;
    private LandedState landedState = LandedState.START;

    private boolean deliveryCompleted = false;
    private int time;

    public FlightSimulation(Drone drone, LatLonAlt home, LatLonAlt client){
        this.drone = drone;
        this.client = client;
        this.home = home;
    }

    public void setCallback(ThreadCallback<Drone> cb){
        this.callback = cb;
    }


    public void run() {

        mission = new Mission(new GlobalPositionProjector());
        drone.setBattery(100);

        while(true) {
            update();
            if (deliveryCompleted)
                break;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        callback.execute(drone);
    }

    private void update(){

        time++;

        switch (droneState){
            case ON_LAND:
                onLandProcess();
                break;

            case TAKING_OFF:
                takingOffProcess();
                break;

            case MISSION:
                missionProcess();
                break;

            case LANDING:
                landingProcess();
                break;
        }

        drone.updateBattery();
        drone.sendTelemetry();
    }

    private void landingProcess(){
        log.info(String.format("landing , %d", time));
        LatLonAlt currentPosition = drone.getPosition();
        if(currentPosition.alt > 1)
        {
            LatLonAlt newPosition = mission.changeAlt(5, currentPosition);
            drone.setPosition(newPosition);
        }
        else
            droneState = DroneState.ON_LAND;
    }

    private void takingOffProcess() {
        log.info(String.format("taking off , %d", time));
        LatLonAlt currentPosition = drone.getPosition();
        if (currentPosition.alt < 20)
        {
            LatLonAlt newPosition = mission.changeAlt(-5, currentPosition);
            drone.setPosition(newPosition);
        }
        else {
            droneState = DroneState.MISSION;
            drone.setSpeed(mission.getSpeed());
            log.info("ready to fly");
        }
    }


    private void onLandProcess(){

        switch (landedState){

            case START:
                landedState = LandedState.TO_CLIENT;
                mission.init(home, client);
                drone.setPosition(home);
                log.info("take off detected. going to client");
                droneState = DroneState.TAKING_OFF;
                break;

            case TO_CLIENT:
                landedState = LandedState.TO_HOME;
                log.info(String.format("landed on client side , %d", time));
                mission.init(client, home);
                log.info("take off detected. going home");

                droneState = DroneState.TAKING_OFF;
                break;

            case TO_HOME:
                landedState = LandedState.COMPLETED;
                log.info(String.format("landed at home ,  %d", time));
                break;

            case COMPLETED:
                deliveryCompleted = true;
                break;

            default:
                throw new IllegalArgumentException(" no such landed state!");
        }

    }

    private void missionProcess(){
        updatePosition();
        if(mission.completed(drone.getPosition())){
            droneState = DroneState.LANDING;
            drone.setSpeed(0);
        }
    }

    private void updatePosition(){
        LatLonAlt newPosition  = mission.updatePosition(drone.getPosition());
        log.info(String.format("new position: %s , %d", newPosition, time));
        drone.setPosition(newPosition);
    }

}
