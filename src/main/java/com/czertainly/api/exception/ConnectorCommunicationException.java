package com.czertainly.api.exception;

import com.czertainly.api.model.core.connectors.ConnectorDto;

public class ConnectorCommunicationException extends ConnectorException {

    public ConnectorCommunicationException(ConnectorDto connector) {
        super(connector);
    }

    public ConnectorCommunicationException(String message, ConnectorDto connector) {
        super(message, connector);
    }

    public ConnectorCommunicationException(String message, Throwable cause, ConnectorDto connector) {
        super(message, cause, connector);
    }

    public ConnectorCommunicationException(Throwable cause, ConnectorDto connector) {
        super(cause, connector);
    }
}
