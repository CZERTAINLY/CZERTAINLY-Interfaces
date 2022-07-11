package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.connector.discovery.DiscoveryDataRequestDto;
import com.czertainly.api.model.connector.discovery.DiscoveryProviderDto;
import com.czertainly.api.model.connector.discovery.DiscoveryRequestDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class DiscoveryApiClient extends BaseApiClient {

    private static final String DISCOVERY_BASE_CONTEXT = "/v1/discoveryProvider/discover";
    private static final String DISCOVERY_GET_CONTEXT = DISCOVERY_BASE_CONTEXT + "/{uuid}";

    public DiscoveryApiClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public DiscoveryProviderDto discoverCertificates(ConnectorDto connector, DiscoveryRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + DISCOVERY_BASE_CONTEXT)
                .body(Mono.just(requestDto), DiscoveryRequestDto.class)
                .retrieve()
                .toEntity(DiscoveryProviderDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public DiscoveryProviderDto getDiscoveryData(ConnectorDto connector, DiscoveryDataRequestDto requestDto, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                        .uri(connector.getUrl() + DISCOVERY_GET_CONTEXT, uuid)
                        .body(Mono.just(requestDto), DiscoveryDataRequestDto.class)
                        .retrieve()
                        .toEntity(DiscoveryProviderDto.class)
                        .block().getBody(),
                request,
                connector);
    }
}
