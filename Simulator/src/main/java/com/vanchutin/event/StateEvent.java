package com.vanchutin.event;


public class StateEvent extends Event {

    public StateEvent(int id, String msg) {
        super(id, EventType.STATE);
    }
}
