package com.vanchutin.generator;

import com.vanchutin.event.Event;
import com.vanchutin.event.EventType;



public class EventGenerator {

    private static GeneratorState state = GeneratorState.GENERATING_ERROR;
    private static int currentDeviceId=1;
    private static int currentComponentId=1;


    public Event generate(){
        Event event = null;
        switch (state)
        {
            case GENERATING_ERROR:
                event = generateEvent(EventType.ERROR);
                break;

            case GENERATING_RESTORE:
                event = generateEvent(EventType.RESTORE);
                break;

            default:
                throw new IllegalArgumentException("Wrong event type argument");
        }
        return event;
    }

    private Event generateEvent(String eventType){
        Event event = new Event(eventType, currentDeviceId, currentComponentId++);
        if(currentComponentId>5) {
            currentComponentId = 1;
            currentDeviceId++;
            if(currentDeviceId>5) {
                currentDeviceId = 1;
                changeState();
            }
        }

        return event;
    }

    private void changeState(){
        switch (state){
            case GENERATING_ERROR:
                state = GeneratorState.GENERATING_RESTORE;
                break;
            case GENERATING_RESTORE:
                state = GeneratorState.GENERATING_ERROR;
                break;
            default:
                throw new IllegalArgumentException("Wrong generator state argument");
        }
    }




}
