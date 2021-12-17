package com.czertainly.api;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.ca.AddEndEntityRequestDto;
import com.czertainly.api.model.ca.EditEndEntityRequestDto;
import com.czertainly.api.model.ca.EndEntityDto;
import com.czertainly.api.model.connector.ConnectorDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class EndEntityApiClient extends BaseApiClient {

    private static final String END_ENTITY_BASE_CONTEXT = "/v1/caConnector/authorities/{authorityId}/endEntityProfiles/{endEntityProfileName}/endEntities";
    private static final String END_ENTITY_IDENTIFIED_CONTEXT = END_ENTITY_BASE_CONTEXT + "/{endEntityName}";
    private static final String END_ENTITY_RESET_PASSWORD_CONTEXT = END_ENTITY_IDENTIFIED_CONTEXT + "/resetPassword";

    public EndEntityApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<EndEntityDto> listEntities(ConnectorDto connector, String authorityUuid, String endEntityProfileName) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_BASE_CONTEXT, authorityUuid, endEntityProfileName)
                .retrieve()
                .toEntityList(EndEntityDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public EndEntityDto getEndEntity(ConnectorDto connector, String authorityUuid, String endEntityProfileName, String endEntityName) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_IDENTIFIED_CONTEXT, authorityUuid, endEntityProfileName, endEntityName)
                .retrieve()
                .toEntity(EndEntityDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public void createEndEntity(ConnectorDto connector, String authorityUuid, String endEntityProfileName, AddEndEntityRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_BASE_CONTEXT, authorityUuid, endEntityProfileName)
                .body(Mono.just(requestDto), AddEndEntityRequestDto.class)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public void updateEndEntity(ConnectorDto connector, String authorityUuid, String endEntityProfileName, String endEntityName, EditEndEntityRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_IDENTIFIED_CONTEXT, authorityUuid, endEntityProfileName, endEntityName)
                .body(Mono.just(requestDto), EditEndEntityRequestDto.class)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public void revokeAndDeleteEndEntity(ConnectorDto connector, String authorityUuid, String endEntityProfileName, String endEntityName) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.DELETE, connector.getAuthType(), connector.getAuthAttributes());

        processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_IDENTIFIED_CONTEXT, authorityUuid, endEntityProfileName, endEntityName)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public void resetPassword(ConnectorDto connector, String authorityUuid, String endEntityProfileName, String endEntityName) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.PUT, connector.getAuthType(), connector.getAuthAttributes());

        processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_RESET_PASSWORD_CONTEXT, authorityUuid, endEntityProfileName, endEntityName)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }
}
