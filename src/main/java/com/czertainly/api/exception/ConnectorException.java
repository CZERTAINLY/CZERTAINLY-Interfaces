package com.czertainly.api.exception;

import com.czertainly.api.model.connector.ConnectorDto;

public class ConnectorException extends Exception {

    protected ConnectorDto connector;

    public ConnectorException() {
        super();
    }

    public ConnectorException(String message) {
        super(message);
    }

    public ConnectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectorException(Throwable cause) {
        super(cause);
    }

    public ConnectorException(ConnectorDto connector) {
        super();
        this.connector = connector;
    }

    public ConnectorException(String message, ConnectorDto connector) {
        super(message);
        this.connector = connector;
    }

    public ConnectorException(String message, Throwable cause, ConnectorDto connector) {
        super(message, cause);
        this.connector = connector;
    }

    public ConnectorException(Throwable cause, ConnectorDto connector) {
        super(cause);
        this.connector = connector;
    }


    public ConnectorDto getConnector() {
        return connector;
    }

    public void setConnector(ConnectorDto connector) {
        this.connector = connector;
    }
}
