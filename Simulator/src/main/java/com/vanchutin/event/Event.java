package com.vanchutin.event;

import lombok.Data;

public @Data
abstract class Event {
    protected int id;
    protected String eventType;
    protected Event(int id, String eventType){
        this.id = id;
        this.eventType = eventType;
    }
}
