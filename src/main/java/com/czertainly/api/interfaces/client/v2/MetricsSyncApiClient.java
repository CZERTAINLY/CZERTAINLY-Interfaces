package com.czertainly.api.interfaces.client.v2;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.core.connector.ConnectorDto;

/**
 * Sync interface for v2 Metrics API client operations.
 * This interface is implemented by both REST and MQ clients.
 */
public interface MetricsSyncApiClient {

    String getMetrics(ConnectorDto connector) throws ConnectorException;
}
