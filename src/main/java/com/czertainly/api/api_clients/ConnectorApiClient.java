package com.czertainly.api.api_clients;

import java.util.List;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.core.connectors.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import com.czertainly.api.model.client.connector.InfoResponse;

public class ConnectorApiClient extends BaseApiClient {

    private static final String CONNECTOR_BASE_CONTEXT = "/v1";

    private static final ParameterizedTypeReference<List<InfoResponse>> MAP_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public ConnectorApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<InfoResponse> listSupportedFunctions(ConnectorDto connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + CONNECTOR_BASE_CONTEXT)
                .retrieve()
                .toEntity(MAP_TYPE_REF)
                .block().getBody(),
                request,
                connector);
    }
}
