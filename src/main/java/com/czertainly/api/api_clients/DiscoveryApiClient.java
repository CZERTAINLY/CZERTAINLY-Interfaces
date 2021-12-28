package com.czertainly.api.api_clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.connector.discovery.DiscoveryProviderRequestDto;
import com.czertainly.api.model.connector.discovery.GetDiscoveryProviderRequestDto;
import com.czertainly.api.model.core.connectors.ConnectorDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;


import com.czertainly.api.model.connector.discovery.DiscoveryProviderDto;

import reactor.core.publisher.Mono;

public class DiscoveryApiClient extends BaseApiClient {

    private static final String DISCOVERY_BASE_CONTEXT = "/v1/discoveryProvider/discover";
    private static final String DISCOVERY_GET_CONTEXT = DISCOVERY_BASE_CONTEXT + "/{uuid}";

    public DiscoveryApiClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public DiscoveryProviderDto discoverCertificate(ConnectorDto connector, DiscoveryProviderRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + DISCOVERY_BASE_CONTEXT)
                .body(Mono.just(requestDto), DiscoveryProviderRequestDto.class)
                .retrieve()
                .toEntity(DiscoveryProviderDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public DiscoveryProviderDto getDiscoveryData(ConnectorDto connector, GetDiscoveryProviderRequestDto requestDto, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                        .uri(connector.getUrl() + DISCOVERY_GET_CONTEXT, uuid)
                        .body(Mono.just(requestDto), GetDiscoveryProviderRequestDto.class)
                        .retrieve()
                        .toEntity(DiscoveryProviderDto.class)
                        .block().getBody(),
                request,
                connector);
    }
}
