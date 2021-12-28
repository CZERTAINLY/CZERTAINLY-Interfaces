package com.czertainly.api.api_clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.commons.HealthDto;
import com.czertainly.api.model.core.connectors.ConnectorDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

public class HealthApiClient extends BaseApiClient {

    private static final String HEALTH_BASE_CONTEXT = "/v1/health";

    public HealthApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public HealthDto checkHealth(ConnectorDto connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + HEALTH_BASE_CONTEXT)
                .retrieve()
                .toEntity(HealthDto.class)
                .block().getBody(),
                request,
                connector);
    }
}
