package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.common.HealthDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.TrustManager;

public class HealthApiClient extends BaseApiClient {

    private static final String HEALTH_BASE_CONTEXT = "/v1/health";

    public HealthApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    public HealthDto checkHealth(ApiClientConnectorInfo connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, false);

        return processRequest(r -> r
                .uri(connector.getUrl() + HEALTH_BASE_CONTEXT)
                .retrieve()
                .toEntity(HealthDto.class)
                .block().getBody(),
                request,
                connector);
    }
}
