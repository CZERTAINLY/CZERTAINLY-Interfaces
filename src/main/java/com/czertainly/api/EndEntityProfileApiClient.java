package com.czertainly.api;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.NameAndIdDto;
import com.czertainly.api.model.connector.ConnectorDto;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class EndEntityProfileApiClient extends BaseApiClient {

    private static final String END_ENTITY_PROFILE_BASE_CONTEXT = "/v1/caConnector/authorities/{authorityId}/endEntityProfiles";
    private static final String END_ENTITY_PROFILE_IDENTIFIED_CONTEXT = END_ENTITY_PROFILE_BASE_CONTEXT + "/{endEntityProfileId}";
    private static final String END_ENTITY_PROFILE_CERT_PROFILE_CONTEXT = END_ENTITY_PROFILE_IDENTIFIED_CONTEXT + "/certificateprofiles";
    private static final String END_ENTITY_PROFILE_CAS_IN_PROFILE_CONTEXT = END_ENTITY_PROFILE_IDENTIFIED_CONTEXT + "/cas";

    public EndEntityProfileApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<NameAndIdDto> listEndEntityProfiles(ConnectorDto connector, Long authorityId) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_PROFILE_BASE_CONTEXT, authorityId)
                .retrieve()
                .toEntityList(NameAndIdDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<NameAndIdDto> listCertificateProfiles(ConnectorDto connector, Long authorityId, int endEntityProfileId) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_PROFILE_CERT_PROFILE_CONTEXT, authorityId, endEntityProfileId)
                .retrieve()
                .toEntityList(NameAndIdDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<NameAndIdDto> listCAsInProfile(ConnectorDto connector, Long authorityId, int endEntityProfileId) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + END_ENTITY_PROFILE_CAS_IN_PROFILE_CONTEXT, authorityId, endEntityProfileId)
                .retrieve()
                .toEntityList(NameAndIdDto.class)
                .block().getBody(),
                request,
                connector);
    }
}
