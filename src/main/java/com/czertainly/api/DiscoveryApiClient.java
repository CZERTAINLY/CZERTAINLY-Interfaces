package com.czertainly.api;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.connector.ConnectorDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;


import com.czertainly.api.model.discovery.DiscoveryProviderDto;

import reactor.core.publisher.Mono;

public class DiscoveryApiClient extends BaseApiClient {

    private static final String DISCOVERY_BASE_CONTEXT = "/v1/discoveryProvider/discover";

    public DiscoveryApiClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public DiscoveryProviderDto discoverCertificate(ConnectorDto connector, DiscoveryProviderDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + DISCOVERY_BASE_CONTEXT)
                .body(Mono.just(requestDto), DiscoveryProviderDto.class)
                .retrieve()
                .toEntity(DiscoveryProviderDto.class)
                .block().getBody(),
                request,
                connector);
    }
}
