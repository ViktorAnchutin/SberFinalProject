package com.vanchutin.event;


import lombok.Data;

public @Data
class StateEvent extends Event {

    public StateEvent(int id, String msg) {
        super(id, EventType.STATE);
        this.message = msg;
    }

    private String message;
}
