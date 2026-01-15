package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.entity.EntityInstanceDto;
import com.czertainly.api.model.connector.entity.EntityInstanceRequestDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.czertainly.api.interfaces.client.EntityInstanceSyncApiClient;

import javax.net.ssl.TrustManager;
import java.util.List;

public class EntityInstanceApiClient extends BaseApiClient implements EntityInstanceSyncApiClient {

    private static final String ENTITY_INSTANCE_BASE_CONTEXT = "/v1/entityProvider/entities";
    private static final String ENTITY_INSTANCE_IDENTIFIED_CONTEXT = ENTITY_INSTANCE_BASE_CONTEXT + "/{entityUuid}";
    private static final String ENTITY_INSTANCE_LOCATION_ATTRS_CONTEXT = ENTITY_INSTANCE_IDENTIFIED_CONTEXT + "/location/attributes";
    private static final String ENTITY_INSTANCE_LOCATION_ATTRS_VALIDATE_CONTEXT = ENTITY_INSTANCE_LOCATION_ATTRS_CONTEXT + "/validate";

    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public EntityInstanceApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    @Override
    public List<EntityInstanceDto> listEntityInstances(ConnectorDto connector) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + ENTITY_INSTANCE_BASE_CONTEXT)
                .retrieve()
                .toEntityList(EntityInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public EntityInstanceDto getEntityInstance(ConnectorDto connector, String entityUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + ENTITY_INSTANCE_IDENTIFIED_CONTEXT, entityUuid)
                .retrieve()
                .toEntity(EntityInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public EntityInstanceDto createEntityInstance(ConnectorDto connector, EntityInstanceRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + ENTITY_INSTANCE_BASE_CONTEXT)
                .body(Mono.just(requestDto), EntityInstanceRequestDto.class)
                .retrieve()
                .toEntity(EntityInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public EntityInstanceDto updateEntityInstance(ConnectorDto connector, String entityUuid, EntityInstanceRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.PUT, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + ENTITY_INSTANCE_IDENTIFIED_CONTEXT, entityUuid)
                .body(Mono.just(requestDto), EntityInstanceRequestDto.class)
                .retrieve()
                .toEntity(EntityInstanceDto.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public void removeEntityInstance(ConnectorDto connector, String entityUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.DELETE, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + ENTITY_INSTANCE_IDENTIFIED_CONTEXT, entityUuid)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }


    @Override
    public List<BaseAttribute> listLocationAttributes(ConnectorDto connector, String entityUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + ENTITY_INSTANCE_LOCATION_ATTRS_CONTEXT, entityUuid)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public void validateLocationAttributes(ConnectorDto connector, String entityUuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + ENTITY_INSTANCE_LOCATION_ATTRS_VALIDATE_CONTEXT, entityUuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }
}