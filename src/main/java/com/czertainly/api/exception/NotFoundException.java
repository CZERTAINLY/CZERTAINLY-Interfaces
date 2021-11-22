package com.czertainly.api.exception;

import com.czertainly.api.model.connector.ConnectorDto;

public class NotFoundException extends ConnectorException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String objectType, Object identifier) {
        super("Object of type '" + objectType + "' identified by " + identifier + " was not found or is not enabled.");
    }

    public NotFoundException(Class<?> objectClass, Object identifier) {
        this(objectClass.getSimpleName(), identifier);
    }

    public NotFoundException(ConnectorDto connector) {
        super(connector);
    }

    public NotFoundException(String message, ConnectorDto connector) {
        super(message, connector);
    }

    public NotFoundException(String objectType, Object identifier, ConnectorDto connector) {
        this(objectType, identifier);
        super.connector = connector;
    }

    public NotFoundException(Class<?> objectClass, Object identifier, ConnectorDto connector) {
        this(objectClass.getSimpleName(), identifier, connector);
    }
}
