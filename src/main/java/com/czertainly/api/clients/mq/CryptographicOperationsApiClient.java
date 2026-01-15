package com.czertainly.api.clients.mq;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.interfaces.client.CryptographicOperationsSyncApiClient;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.operations.*;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CryptographicOperationsApiClient implements CryptographicOperationsSyncApiClient {

    private static final String BASE_PATH = "/v1/cryptographyProvider/tokens";
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";

    private final ProxyClient proxyClient;

    public CryptographicOperationsApiClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    @Override
    public EncryptDataResponseDto encryptData(ConnectorDto connector, String uuid, String keyUuid, CipherDataRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid + "/encrypt";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, EncryptDataResponseDto.class);
    }

    @Override
    public DecryptDataResponseDto decryptData(ConnectorDto connector, String uuid, String keyUuid, CipherDataRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid + "/decrypt";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, DecryptDataResponseDto.class);
    }

    @Override
    public SignDataResponseDto signData(ConnectorDto connector, String uuid, String keyUuid, SignDataRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid + "/sign";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, SignDataResponseDto.class);
    }

    @Override
    public VerifyDataResponseDto verifyData(ConnectorDto connector, String uuid, String keyUuid, VerifyDataRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid + "/verify";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, VerifyDataResponseDto.class);
    }

    @Override
    public List<BaseAttribute> listRandomAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/random/attributes";
        BaseAttribute[] result = proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, BaseAttribute[].class);
        return Arrays.asList(result);
    }

    @Override
    public void validateRandomAttributes(ConnectorDto connector, String uuid, List<RequestAttributeDto> attributes) throws ValidationException, ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/random/attributes/validate";
        proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, attributes, Void.class);
    }

    @Override
    public RandomDataResponseDto randomData(ConnectorDto connector, String uuid, RandomDataRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/keys/random";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, RandomDataResponseDto.class);
    }

    // Async variants
    public CompletableFuture<EncryptDataResponseDto> encryptDataAsync(ConnectorDto connector, String uuid, String keyUuid, CipherDataRequestDto requestDto) {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid + "/encrypt";
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_POST, requestDto, EncryptDataResponseDto.class);
    }

    public CompletableFuture<DecryptDataResponseDto> decryptDataAsync(ConnectorDto connector, String uuid, String keyUuid, CipherDataRequestDto requestDto) {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid + "/decrypt";
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_POST, requestDto, DecryptDataResponseDto.class);
    }

    public CompletableFuture<SignDataResponseDto> signDataAsync(ConnectorDto connector, String uuid, String keyUuid, SignDataRequestDto requestDto) {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid + "/sign";
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_POST, requestDto, SignDataResponseDto.class);
    }

    public CompletableFuture<VerifyDataResponseDto> verifyDataAsync(ConnectorDto connector, String uuid, String keyUuid, VerifyDataRequestDto requestDto) {
        String path = BASE_PATH + "/" + uuid + "/keys/" + keyUuid + "/verify";
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_POST, requestDto, VerifyDataResponseDto.class);
    }
}
