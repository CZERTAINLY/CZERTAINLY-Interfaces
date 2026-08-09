package com.otilm.api.interfaces.client.v2;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.exception.ConnectorException;

/**
 * Sync interface for v2 Metrics API client operations. This interface is implemented by both REST and MQ clients.
 */
public interface MetricsSyncApiClient {

    String getMetrics(ApiClientConnectorInfo connector) throws ConnectorException;
}
