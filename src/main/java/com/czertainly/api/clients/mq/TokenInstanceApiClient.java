package com.czertainly.api.clients.mq;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.interfaces.client.v1.TokenInstanceSyncApiClient;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceDto;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceRequestDto;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceStatusDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TokenInstanceApiClient implements TokenInstanceSyncApiClient {

    private static final String BASE_PATH = "/v1/cryptographyProvider/tokens";
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_METHOD_DELETE = "DELETE";
    private static final String HTTP_METHOD_PATCH = "PATCH";

    private final ProxyClient proxyClient;

    public TokenInstanceApiClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    @Override
    public List<TokenInstanceDto> listTokenInstances(ConnectorDto connector) throws ConnectorException {
        TokenInstanceDto[] result = proxyClient.sendRequest(connector, BASE_PATH, HTTP_METHOD_GET, null, TokenInstanceDto[].class);
        return Arrays.asList(result);
    }

    @Override
    public TokenInstanceDto getTokenInstance(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid;
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, TokenInstanceDto.class);
    }

    @Override
    public TokenInstanceDto createTokenInstance(ConnectorDto connector, TokenInstanceRequestDto requestDto) throws ConnectorException {
        return proxyClient.sendRequest(connector, BASE_PATH, HTTP_METHOD_POST, requestDto, TokenInstanceDto.class);
    }

    @Override
    public TokenInstanceDto updateTokenInstance(ConnectorDto connector, String uuid, TokenInstanceRequestDto requestDto) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid;
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, requestDto, TokenInstanceDto.class);
    }

    @Override
    public void removeTokenInstance(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid;
        proxyClient.sendRequest(connector, path, HTTP_METHOD_DELETE, null, Void.class);
    }

    @Override
    public TokenInstanceStatusDto getTokenInstanceStatus(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/status";
        return proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, TokenInstanceStatusDto.class);
    }

    @Override
    public List<BaseAttribute> listTokenProfileAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/tokenProfile/attributes";
        BaseAttribute[] result = proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, BaseAttribute[].class);
        return Arrays.asList(result);
    }

    @Override
    public void validateTokenProfileAttributes(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/tokenProfile/attributes/validate";
        proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, attributes, Void.class);
    }

    @Override
    public List<BaseAttribute> listTokenInstanceActivationAttributes(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/activate/attributes";
        BaseAttribute[] result = proxyClient.sendRequest(connector, path, HTTP_METHOD_GET, null, BaseAttribute[].class);
        return Arrays.asList(result);
    }

    @Override
    public void validateTokenInstanceActivationAttributes(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ValidationException, ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/activate/attributes/validate";
        proxyClient.sendRequest(connector, path, HTTP_METHOD_POST, attributes, Void.class);
    }

    @Override
    public void activateTokenInstance(ConnectorDto connector, String uuid, List<RequestAttribute> attributes) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/activate";
        proxyClient.sendRequest(connector, path, HTTP_METHOD_PATCH, attributes, Void.class);
    }

    @Override
    public void deactivateTokenInstance(ConnectorDto connector, String uuid) throws ConnectorException {
        String path = BASE_PATH + "/" + uuid + "/deactivate";
        proxyClient.sendRequest(connector, path, HTTP_METHOD_PATCH, null, Void.class);
    }

    // Async variants for key methods
    public CompletableFuture<List<TokenInstanceDto>> listTokenInstancesAsync(ConnectorDto connector) {
        return proxyClient.sendRequestAsync(connector, BASE_PATH, HTTP_METHOD_GET, null, TokenInstanceDto[].class)
                .thenApply(Arrays::asList);
    }

    public CompletableFuture<TokenInstanceDto> getTokenInstanceAsync(ConnectorDto connector, String uuid) {
        String path = BASE_PATH + "/" + uuid;
        return proxyClient.sendRequestAsync(connector, path, HTTP_METHOD_GET, null, TokenInstanceDto.class);
    }

    public CompletableFuture<TokenInstanceDto> createTokenInstanceAsync(ConnectorDto connector, TokenInstanceRequestDto requestDto) {
        return proxyClient.sendRequestAsync(connector, BASE_PATH, HTTP_METHOD_POST, requestDto, TokenInstanceDto.class);
    }
}
