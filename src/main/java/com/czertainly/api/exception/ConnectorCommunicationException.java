package com.czertainly.api.exception;

import com.czertainly.api.clients.ApiClientConnectorInfo;

public class ConnectorCommunicationException extends ConnectorException {

    public ConnectorCommunicationException(String message, ApiClientConnectorInfo connector) {
        super(message, connector);
    }

    public ConnectorCommunicationException(String message, Throwable cause, ApiClientConnectorInfo connector) {
        super(message, cause, connector);
    }
}
