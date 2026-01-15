package com.czertainly.api.clients.mq;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.interfaces.client.KeyManagementSyncApiClient;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.key.CreateKeyRequestDto;
import com.czertainly.api.model.connector.cryptography.key.KeyDataResponseDto;
import com.czertainly.api.model.connector.cryptography.key.KeyPairDataResponseDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class KeyManagementApiClient implements KeyManagementSyncApiClient {

    private static final String BASE_PATH = "/v1/cryptographyProvider/tokens";
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_METHOD_DELETE = "DELETE";

    private final ProxyClient proxyClient;

    public KeyManagementApiClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    @Override
    public List<BaseAttribute> listCreateSecretKeyAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/secret/attributes";
        BaseAttribute[] result = proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, BaseAttribute[].class);
        return Arrays.asList(result);
    }

    @Override
    public void validateCreateSecretKeyAttributes(ConnectorDto connector, String uuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/secret/attributes/validate";
        proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, attributes, Void.class);
    }

    @Override
    public KeyDataResponseDto createSecretKey(ConnectorDto connector, String uuid, CreateKeyRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/secret";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, KeyDataResponseDto.class);
    }

    @Override
    public List<BaseAttribute> listCreateKeyPairAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/pair/attributes";
        BaseAttribute[] result = proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, BaseAttribute[].class);
        return Arrays.asList(result);
    }

    @Override
    public void validateCreateKeyPairAttributes(ConnectorDto connector, String uuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/pair/attributes/validate";
        proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, attributes, Void.class);
    }

    @Override
    public KeyPairDataResponseDto createKeyPair(ConnectorDto connector, String uuid, CreateKeyRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/pair";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, KeyPairDataResponseDto.class);
    }

    @Override
    public List<KeyDataResponseDto> listKeys(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys";
        KeyDataResponseDto[] result = proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, KeyDataResponseDto[].class);
        return Arrays.asList(result);
    }

    @Override
    public KeyDataResponseDto getKey(ConnectorDto connector, String uuid, String keyUuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid;
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, KeyDataResponseDto.class);
    }

    @Override
    public void destroyKey(ConnectorDto connector, String uuid, String keyUuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid;
        proxyClient.sendRequest(connector, path, HTTP_METHOD_DELETE, null, Void.class);
    }

    // Async variants
    public CompletableFuture<List<KeyDataResponseDto>> listKeysAsync(ConnectorDto connector, String uuid) {
        String path = BASE_PATH + "/" + uuid + "/keys";
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_GET, null, KeyDataResponseDto[].class)
                .thenApply(Arrays::asList);
    }

    public CompletableFuture<KeyPairDataResponseDto> createKeyPairAsync(ConnectorDto connector, String uuid, CreateKeyRequestDto requestDto) {
        String path = BASE_PATH + "/" + uuid + "/keys/pair";
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_POST, requestDto, KeyPairDataResponseDto.class);
    }
}
