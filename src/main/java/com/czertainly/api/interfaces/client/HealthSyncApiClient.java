package com.czertainly.api.interfaces.client;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.common.HealthDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

/**
 * Interface for synchronous health check operations against connectors.
 * Implementations can use REST (direct HTTP) or MQ (proxy) communication.
 */
public interface HealthSyncApiClient {

    /**
     * Check health status of a connector.
     *
     * @param connector Connector configuration
     * @return Health status
     * @throws ConnectorException If request fails
     */
    HealthDto checkHealth(ConnectorDto connector) throws ConnectorException;
}
