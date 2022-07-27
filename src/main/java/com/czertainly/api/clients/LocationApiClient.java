package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.common.attribute.AttributeDefinition;
import com.czertainly.api.model.common.attribute.RequestAttributeDto;
import com.czertainly.api.model.connector.entity.GenerateCsrRequestDto;
import com.czertainly.api.model.connector.entity.GenerateCsrResponseDto;
import com.czertainly.api.model.connector.entity.LocationDetailRequestDto;
import com.czertainly.api.model.connector.entity.LocationDetailResponseDto;
import com.czertainly.api.model.connector.entity.PushCertificateRequestDto;
import com.czertainly.api.model.connector.entity.PushCertificateResponseDto;
import com.czertainly.api.model.connector.entity.RemoveCertificateRequestDto;
import com.czertainly.api.model.connector.entity.RemoveCertificateResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class LocationApiClient extends BaseApiClient {

    private static final String LOCATION_BASE_CONTEXT = "/v1/entityProvider/entities/{entityUuid}/locations";
    private static final String LOCATION_PUSH_CONTEXT = LOCATION_BASE_CONTEXT + "/push";
    private static final String LOCATION_PUSH_ATTRS_CONTEXT = LOCATION_BASE_CONTEXT + "/push/attributes";
    private static final String LOCATION_PUSH_ATTRS_VALIDATE_CONTEXT = LOCATION_BASE_CONTEXT + "/push/attributes/validate";
    private static final String LOCATION_REMOVE_CONTEXT = LOCATION_BASE_CONTEXT + "/remove";
    private static final String LOCATION_CSR_CONTEXT = LOCATION_BASE_CONTEXT + "/csr";
    private static final String LOCATION_CSR_ATTRS_CONTEXT = LOCATION_BASE_CONTEXT + "/csr/attributes";
    private static final String LOCATION_CSR_ATTRS_VALIDATE_CONTEXT = LOCATION_BASE_CONTEXT + "/csr/attributes/validate";

    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public LocationApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public LocationDetailResponseDto getLocationDetail(ConnectorDto connector, String entityUuid, LocationDetailRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + LOCATION_BASE_CONTEXT, entityUuid)
                .body(Mono.just(requestDto), LocationDetailRequestDto.class)
                .retrieve()
                .toEntity(LocationDetailResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public PushCertificateResponseDto pushCertificateToLocation(ConnectorDto connector, String entityUuid, PushCertificateRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + LOCATION_PUSH_CONTEXT, entityUuid)
                .body(Mono.just(requestDto), PushCertificateRequestDto.class)
                .retrieve()
                .toEntity(PushCertificateResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<AttributeDefinition> listPushCertificateAttributes(ConnectorDto connector, String entityUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + LOCATION_PUSH_ATTRS_CONTEXT, entityUuid)
                .retrieve()
                .toEntityList(AttributeDefinition.class)
                .block().getBody(),
                request,
                connector);
    }

    public void validatePushCertificateAttributes(ConnectorDto connector, String entityUuid, List<RequestAttributeDto> pushAttributes) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + LOCATION_PUSH_ATTRS_VALIDATE_CONTEXT, entityUuid)
                .body(Mono.just(pushAttributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public RemoveCertificateResponseDto removeCertificateFromLocation(ConnectorDto connector, String entityUuid, RemoveCertificateRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + LOCATION_REMOVE_CONTEXT, entityUuid)
                .body(Mono.just(requestDto), RemoveCertificateRequestDto.class)
                .retrieve()
                .toEntity(RemoveCertificateResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public GenerateCsrResponseDto generateCsrLocation(ConnectorDto connector, String entityUuid, GenerateCsrRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + LOCATION_CSR_CONTEXT, entityUuid)
                .body(Mono.just(requestDto), GenerateCsrRequestDto.class)
                .retrieve()
                .toEntity(GenerateCsrResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<AttributeDefinition> listGenerateCsrAttributes(ConnectorDto connector, String entityUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + LOCATION_CSR_ATTRS_CONTEXT, entityUuid)
                .retrieve()
                .toEntityList(AttributeDefinition.class)
                .block().getBody(),
                request,
                connector);
    }

    public void validateGenerateCsrAttributes(ConnectorDto connector, String entityUuid, List<RequestAttributeDto> pushAttributes) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + LOCATION_CSR_ATTRS_VALIDATE_CONTEXT, entityUuid)
                .body(Mono.just(pushAttributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

}