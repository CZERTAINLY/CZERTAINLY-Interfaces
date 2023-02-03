package com.czertainly.api.clients.cryptography;

import com.czertainly.api.clients.BaseApiClient;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.operations.*;
import com.czertainly.api.model.core.connector.ConnectorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.net.ssl.TrustManager;
import java.util.List;

public class CryptographicOperationsApiClient extends BaseApiClient {

    private static final String CRYPTOP_BASE_CONTEXT = "/v1/cryptographyProvider/tokens/{uuid}/keys";
    private static final String CRYPTOP_ENCRYPT_CONTEXT = CRYPTOP_BASE_CONTEXT + "/{keyUuid}/encrypt";
    private static final String CRYPTOP_DECRYPT_CONTEXT = CRYPTOP_BASE_CONTEXT + "/{keyUuid}/decrypt";
    private static final String CRYPTOP_SIGN_CONTEXT = CRYPTOP_BASE_CONTEXT + "/{keyUuid}/sign";
    private static final String CRYPTOP_VERIFY_CONTEXT = CRYPTOP_BASE_CONTEXT + "/{keyUuid}/verify";
    private static final String CRYPTOP_RANDOM_CONTEXT = CRYPTOP_BASE_CONTEXT + "/random";
    private static final String CRYPTOP_RANDOM_ATTRS_CONTEXT = CRYPTOP_RANDOM_CONTEXT + "/attributes";
    private static final String CRYPTOP_RANDOM_ATTRS_VALIDATE_CONTEXT = CRYPTOP_RANDOM_ATTRS_CONTEXT + "/validate";

    private static final ParameterizedTypeReference<List<RequestAttributeDto>> ATTRIBUTE_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    public CryptographicOperationsApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    public EncryptDataResponseDto encryptData(ConnectorDto connector, String uuid, String keyUuid, CipherDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_ENCRYPT_CONTEXT, uuid, keyUuid)
                .body(Mono.just(requestDto), CipherDataRequestDto.class)
                .retrieve()
                .toEntity(EncryptDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public DecryptDataResponseDto decryptData(ConnectorDto connector, String uuid, String keyUuid, CipherDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_DECRYPT_CONTEXT, uuid, keyUuid)
                .body(Mono.just(requestDto), CipherDataRequestDto.class)
                .retrieve()
                .toEntity(DecryptDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }


    public SignDataResponseDto signData(ConnectorDto connector, String uuid, String keyUuid, SignDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_SIGN_CONTEXT, uuid, keyUuid)
                .body(Mono.just(requestDto), SignDataRequestDto.class)
                .retrieve()
                .toEntity(SignDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public VerifyDataResponseDto verifyData(ConnectorDto connector, String uuid, String keyUuid, VerifyDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_VERIFY_CONTEXT, uuid, keyUuid)
                .body(Mono.just(requestDto), VerifyDataRequestDto.class)
                .retrieve()
                .toEntity(VerifyDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

    public List<BaseAttribute> listRandomAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.GET, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_RANDOM_ATTRS_CONTEXT, uuid)
                .retrieve()
                .toEntityList(BaseAttribute.class)
                .block().getBody(),
                request,
                connector);
    }

    public void validateRandomAttributes(ConnectorDto connector, String uuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_RANDOM_ATTRS_VALIDATE_CONTEXT, uuid)
                .body(Mono.just(attributes), ATTRIBUTE_LIST_TYPE_REF)
                .retrieve()
                .toEntity(Void.class)
                .block().getBody(),
                request,
                connector);
    }

    public RandomDataResponseDto randomData(ConnectorDto connector, String uuid, RandomDataRequestDto requestDto) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);

        return processRequest(r -> r
                .uri(connector.getUrl() + CRYPTOP_RANDOM_CONTEXT, uuid)
                .body(Mono.just(requestDto), RandomDataRequestDto.class)
                .retrieve()
                .toEntity(RandomDataResponseDto.class)
                .block().getBody(),
                request,
                connector);
    }

}