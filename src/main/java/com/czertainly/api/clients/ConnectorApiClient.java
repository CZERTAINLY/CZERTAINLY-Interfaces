package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.client.connector.InfoResponse;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.TrustManager;
import java.util.List;

public class ConnectorApiClient extends BaseApiClient {

    private static final String CONNECTOR_BASE_CONTEXT = "/v1";

    private static final ParameterizedTypeReference<List<InfoResponse>> MAP_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public ConnectorApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    public List<InfoResponse> listSupportedFunctions(ConnectorDto connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, false);

        return processRequest(r -> r
                        .uri(connector.getUrl() + CONNECTOR_BASE_CONTEXT)
                        .retrieve()
                        .toEntity(MAP_TYPE_REF)
                        .block().getBody(),
                request,
                connector);
    }
}
