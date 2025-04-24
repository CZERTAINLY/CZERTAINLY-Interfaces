package com.czertainly.api.exception;

import com.czertainly.api.model.core.other.ResourceEvent;

public class EventException extends Exception {

    private ResourceEvent event;

    public EventException(String message) {
        super(message);
    }

    public EventException(ResourceEvent event, String message) {
        super(message);
        this.event = event;
    }

}
