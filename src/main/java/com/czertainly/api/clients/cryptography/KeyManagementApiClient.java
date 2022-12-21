package com.czertainly.api.clients.cryptography;

import com.czertainly.api.clients.BaseApiClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.key.CreateKeyRequestDto;
import com.czertainly.api.model.connector.cryptography.key.DestroyKeyRequestDto;
import com.czertainly.api.model.connector.cryptography.key.KeyDataResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class KeyManagementApiClient extends BaseApiClient {

    private static final String KEY_BASE_CONTEXT = "/v1/cryptographyProvider/tokens/{uuid}/keys";
    private static final String KEY_CREATE_CONTEXT = KEY_BASE_CONTEXT + "/create";
    private static final String KEY_CREATE_ATTRIBUTES_CONTEXT = KEY_CREATE_CONTEXT + "/attributes";
    private static final String KEY_CREATE_ATTRIBUTES_VALIDATE_CONTEXT = KEY_CREATE_ATTRIBUTES_CONTEXT + "/validate";
    private static final String KEY_DESTROY_CONTEXT = KEY_BASE_CONTEXT + "/destroy";


    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public KeyManagementApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<BaseAttribute> listCreateKeyAttributes(ConnectorDto connector, String tokenInstanceUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + KEY_CREATE_ATTRIBUTES_CONTEXT, tokenInstanceUuid)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    public void validateCreateKeyAttributes(ConnectorDto connector, String tokenInstanceUuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + KEY_CREATE_ATTRIBUTES_VALIDATE_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public KeyDataResponseDto createKey(ConnectorDto connector, String tokenInstanceUuid, CreateKeyRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + KEY_CREATE_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(requestDto), CreateKeyRequestDto.class)
                .retrieve()
                .toEntity(KeyDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public void destroyKey(ConnectorDto connector, String tokenInstanceUuid, DestroyKeyRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + KEY_DESTROY_CONTEXT, tokenInstanceUuid)
                .body(Mono.just(requestDto), DestroyKeyRequestDto.class)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

}
