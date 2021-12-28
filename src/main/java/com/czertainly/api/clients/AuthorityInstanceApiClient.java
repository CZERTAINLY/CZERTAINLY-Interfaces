package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.RequestAttributeDto;
import com.czertainly.api.model.connector.authority.ConnectorAuthorityInstanceDto;
import com.czertainly.api.model.connector.authority.ConnectorAuthorityInstanceRequestDto;
import com.czertainly.api.model.core.authority.AuthorityInstanceDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class AuthorityInstanceApiClient extends BaseApiClient {

    private static final String AUTHORITY_INSTANCE_BASE_CONTEXT = "/v1/authorityProvider/authorities";
    private static final String AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT = AUTHORITY_INSTANCE_BASE_CONTEXT + "/{uuid}";
    private static final String AUTHORITY_INSTANCE_RA_ATTRS_CONTEXT = AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT + "/raProfile/attributes";
    private static final String AUTHORITY_INSTANCE_RA_ATTRS_VALIDATE_CONTEXT = AUTHORITY_INSTANCE_RA_ATTRS_CONTEXT + "/validate";

    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public AuthorityInstanceApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<ConnectorAuthorityInstanceDto> listAuthorityInstances(ConnectorDto connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_BASE_CONTEXT)
                .retrieve()
                .toEntityList(ConnectorAuthorityInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public ConnectorAuthorityInstanceDto getAuthorityInstance(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT, uuid)
                .retrieve()
                .toEntity(ConnectorAuthorityInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public ConnectorAuthorityInstanceDto createAuthorityInstance(ConnectorDto connector, ConnectorAuthorityInstanceRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_BASE_CONTEXT)
                .body(Mono.just(requestDto), AuthorityInstanceDto.class)
                .retrieve()
                .toEntity(ConnectorAuthorityInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }


    public ConnectorAuthorityInstanceDto updateAuthorityInstance(ConnectorDto connector, String uuid, ConnectorAuthorityInstanceRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT, uuid)
                .body(Mono.just(requestDto), AuthorityInstanceDto.class)
                .retrieve()
                .toEntity(ConnectorAuthorityInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public void removeAuthorityInstance(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.DELETE, connector.getAuthType(), connector.getAuthAttributes());

        processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_IDENTIFIED_CONTEXT, uuid)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }


    public List<AttributeDefinition> listRAProfileAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_RA_ATTRS_CONTEXT, uuid)
                .retrieve()
                .toEntityList(AttributeDefinition.class)
                .block().getBody(),
                request,
                connector);
    }

    public Boolean validateRAProfileAttributes(ConnectorDto connector, String uuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector.getAuthType(), connector.getAuthAttributes());

        return processRequest(r -> r
                .uri(connector.getUrl() + AUTHORITY_INSTANCE_RA_ATTRS_VALIDATE_CONTEXT, uuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Boolean.class)
                .block().getBody(),
                request,
                connector);
    }
}