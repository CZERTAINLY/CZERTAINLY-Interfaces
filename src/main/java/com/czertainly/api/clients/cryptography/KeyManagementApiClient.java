package com.czertainly.api.clients.cryptography;

import com.czertainly.api.clients.BaseApiClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.interfaces.client.v1.KeyManagementSyncApiClient;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.key.CreateKeyRequestDto;
import com.czertainly.api.model.connector.cryptography.key.KeyDataResponseDto;
import com.czertainly.api.model.connector.cryptography.key.KeyPairDataResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.net.ssl.TrustManager;
import java.util.List;

public class KeyManagementApiClient extends BaseApiClient implements KeyManagementSyncApiClient {

    private static final String KEY_BASE_CONTEXT = "/v1/cryptographyProvider/tokens/{uuid}/keys";
    private static final String KEY_CREATE_SECRET_KEY_CONTEXT = KEY_BASE_CONTEXT + "/secret";
    private static final String KEY_CREATE_SECRET_KEY_ATTRIBUTES_CONTEXT = KEY_CREATE_SECRET_KEY_CONTEXT + "/attributes";
    private static final String KEY_CREATE_SECRET_KEY_ATTRIBUTES_VALIDATE_CONTEXT = KEY_CREATE_SECRET_KEY_ATTRIBUTES_CONTEXT + "/validate";
    private static final String KEY_CREATE_KEY_PAIR_CONTEXT = KEY_BASE_CONTEXT + "/pair";
    private static final String KEY_CREATE_KEY_PAIR_ATTRIBUTES_CONTEXT = KEY_CREATE_KEY_PAIR_CONTEXT + "/attributes";
    private static final String KEY_CREATE_KEY_PAIR_ATTRIBUTES_VALIDATE_CONTEXT = KEY_CREATE_KEY_PAIR_ATTRIBUTES_CONTEXT + "/validate";
    private static final String KEY_LIST_CONTEXT = KEY_BASE_CONTEXT;
    private static final String KEY_DETAILS_CONTEXT = KEY_BASE_CONTEXT + "/{keyUuid}";

    private static final ParameterizedTypeReference<List<RequestAttribute>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public KeyManagementApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    @Override
    public List<BaseAttribute> listCreateSecretKeyAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + KEY_CREATE_SECRET_KEY_ATTRIBUTES_CONTEXT, uuid)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public void validateCreateSecretKeyAttributes(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + KEY_CREATE_SECRET_KEY_ATTRIBUTES_VALIDATE_CONTEXT, uuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public KeyDataResponseDto createSecretKey(ConnectorDto connector, String uuid, CreateKeyRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + KEY_CREATE_SECRET_KEY_CONTEXT, uuid)
                .body(Mono.just(requestDto), CreateKeyRequestDto.class)
                .retrieve()
                .toEntity(KeyDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public List<BaseAttribute> listCreateKeyPairAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                        .uri(connector.getUrl() + KEY_CREATE_KEY_PAIR_ATTRIBUTES_CONTEXT, uuid)
                        .retrieve()
                        .toEntityList(BaseAttribute.class)
                        .block().getBody(),
                request,
                connector);
    }

    @Override
    public void validateCreateKeyPairAttributes(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                        .uri(connector.getUrl() + KEY_CREATE_KEY_PAIR_ATTRIBUTES_VALIDATE_CONTEXT, uuid)
                        .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                        .retrieve()
                        .toEntity(Void.class)
                        .block().getBody(),
                request,
                connector);
    }

    @Override
    public KeyPairDataResponseDto createKeyPair(ConnectorDto connector, String uuid, CreateKeyRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + KEY_CREATE_KEY_PAIR_CONTEXT, uuid)
                .body(Mono.just(requestDto), CreateKeyRequestDto.class)
                .retrieve()
                .toEntity(KeyPairDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public List<KeyDataResponseDto> listKeys(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + KEY_LIST_CONTEXT, uuid)
                .retrieve()
                .toEntityList(KeyDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public KeyDataResponseDto getKey(ConnectorDto connector, String uuid, String keyUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + KEY_DETAILS_CONTEXT, uuid, keyUuid)
                .retrieve()
                .toEntity(KeyDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    @Override
    public void destroyKey(ConnectorDto connector, String uuid, String keyUuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.DELETE, connector, true);

        processRequest(r -> r
                        .uri(connector.getUrl() + KEY_DETAILS_CONTEXT, uuid, keyUuid)
                        .retrieve()
                        .toEntity(Void.class)
                        .block().getBody(),
                request,
                connector);
    }

}
