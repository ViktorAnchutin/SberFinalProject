package com.vanchutin.simulation.old;

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

    private TakeOff takeOffProcess = new TakeOff();
    private Landing landingProcess = new Landing();

    private boolean deliveryCompleted = false;


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

        if(landingProcess.update(drone)){
            droneState = DroneState.ON_LAND;
            //drone.sendMessage(StateEventMessage.LANDING_DETECTED);
        }
    }

    private void takingOffProcess() {
        if(takeOffProcess.update(drone)){
            droneState = DroneState.MISSION;
            drone.setSpeed(mission.getSpeed());
            log.info("ready to fly");
            //drone.sendMessage(StateEventMessage.MISSION_STARTED);
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
                //drone.sendMessage(StateEventMessage.TAKEOFF_DETECTED);
                break;

            case TO_CLIENT:
                landedState = LandedState.TO_HOME;
                log.info(String.format("landed on client side"));
                mission.init(client, home);
                log.info("take off detected. going home");
                //drone.sendMessage(StateEventMessage.TAKEOFF_DETECTED);

                droneState = DroneState.TAKING_OFF;
                break;

            case TO_HOME:
                landedState = LandedState.COMPLETED;
                log.info(String.format("landed at home"));
                break;

            case COMPLETED:
                deliveryCompleted = true;
                break;

            default:
                throw new IllegalArgumentException(" no such landed state!");
        }

    }

    private void missionProcess(){
        if(mission.update(drone))
        {
            droneState = DroneState.LANDING;
            drone.setSpeed(0);
           // drone.sendMessage(StateEventMessage.LANDING);
        }
    }



}
