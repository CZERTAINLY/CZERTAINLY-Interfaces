package com.otilm.api.clients.mq.v2;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.clients.mq.ProxyClient;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.interfaces.client.v2.CryptographicOperationsSyncApiClient;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.cryptography.v2.KeyScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.OperationResponseValidator;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * MQ implementation of the connector-facing stateless cryptography v2 operations API.
 */
@SuppressWarnings("java:S1075")
public class CryptographicOperationsApiClient implements CryptographicOperationsSyncApiClient {

    private static final String BASE_PATH = "/v2/cryptographyProvider/operations";
    private static final String POST = "POST";
    private final ProxyClient proxyClient;

    public CryptographicOperationsApiClient(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    @Override
    public List<BaseAttribute> listEncryptAttributes(ApiClientConnectorInfo connector, KeyScopedRequestV2Dto request)
            throws ConnectorException {
        return sendAttributes(connector, "/encrypt/attributes", request, "listEncryptAttributes");
    }

    @Override
    public EncryptDataResponseV2Dto encryptData(ApiClientConnectorInfo connector, CipherDataRequestV2Dto request)
            throws ConnectorException {
        return send(connector, "/encrypt", request, EncryptDataResponseV2Dto.class);
    }

    @Override
    public List<BaseAttribute> listDecryptAttributes(ApiClientConnectorInfo connector, KeyScopedRequestV2Dto request)
            throws ConnectorException {
        return sendAttributes(connector, "/decrypt/attributes", request, "listDecryptAttributes");
    }

    @Override
    public DecryptDataResponseV2Dto decryptData(ApiClientConnectorInfo connector, CipherDataRequestV2Dto request)
            throws ConnectorException {
        return send(connector, "/decrypt", request, DecryptDataResponseV2Dto.class);
    }

    @Override
    public List<BaseAttribute> listSignAttributes(ApiClientConnectorInfo connector,
                                                  KeyScopedRequestV2Dto request)
            throws ConnectorException {
        return sendAttributes(connector, "/sign/attributes", request, "listSignAttributes");
    }

    @Override
    public ResponseEntity<SignDataResponseV2Dto> signData(ApiClientConnectorInfo connector, SignDataRequestV2Dto request)
            throws ConnectorException {
        try {
            ResponseEntity<SignDataResponseV2Dto> response = proxyClient.sendRequestForEntity(
                    connector, BASE_PATH + "/sign", POST, request, SignDataResponseV2Dto.class);
            OperationResponseValidator.validateSign(request.getExecutionMode(), response);
            return response;
        } catch (ConnectorException | RuntimeException e) {
            throw new ConnectorException("Cryptographic operation request failed: signData", connector);
        }
    }

    @Override
    public SignOperationStatusResponseV2Dto getSignStatus(ApiClientConnectorInfo connector,
                                                          SignOperationScopedRequestV2Dto request) throws ConnectorException {
        return send(connector, "/sign/status", request, SignOperationStatusResponseV2Dto.class);
    }

    @Override
    public ResponseEntity<Void> cancelSign(ApiClientConnectorInfo connector, SignOperationScopedRequestV2Dto request)
            throws ConnectorException {
        try {
            return proxyClient.sendRequestForEntity(connector, BASE_PATH + "/sign/cancel", POST, request, Void.class);
        } catch (ConnectorException | RuntimeException e) {
            throw new ConnectorException("Cryptographic operation request failed: cancelSign", connector);
        }
    }

    @Override
    public List<BaseAttribute> listVerifyAttributes(ApiClientConnectorInfo connector, KeyScopedRequestV2Dto request)
            throws ConnectorException {
        return sendAttributes(connector, "/verify/attributes", request, "listVerifyAttributes");
    }

    @Override
    public VerifyDataResponseV2Dto verifyData(ApiClientConnectorInfo connector, VerifyDataRequestV2Dto request)
            throws ConnectorException {
        return send(connector, "/verify", request, VerifyDataResponseV2Dto.class);
    }

    @Override
    public List<BaseAttribute> listRandomAttributes(ApiClientConnectorInfo connector,
                                                    TokenProfileScopedRequestV2Dto request)
            throws ConnectorException {
        return sendAttributes(connector, "/random/attributes", request, "listRandomAttributes");
    }

    @Override
    public RandomDataResponseV2Dto randomData(ApiClientConnectorInfo connector, RandomDataRequestV2Dto request)
            throws ConnectorException {
        return send(connector, "/random", request, RandomDataResponseV2Dto.class);
    }

    private List<BaseAttribute> sendAttributes(ApiClientConnectorInfo connector, String path, Object request,
                                               String operation) throws ConnectorException {
        try {
            BaseAttribute[] response = proxyClient.sendRequest(
                    connector, BASE_PATH + path, POST, request, BaseAttribute[].class);
            if (response == null) {
                throw new ConnectorException("Connector returned an empty attributes response", connector);
            }
            return Arrays.asList(response);
        } catch (ConnectorException | RuntimeException e) {
            throw new ConnectorException("Cryptographic operation request failed: " + operation, connector);
        }
    }

    private <T> T send(ApiClientConnectorInfo connector, String path, Object request, Class<T> type)
            throws ConnectorException {
        try {
            return proxyClient.sendRequest(connector, BASE_PATH + path, POST, request, type);
        } catch (ConnectorException | RuntimeException e) {
            throw new ConnectorException("Cryptographic operation request failed: " + path, connector);
        }
    }
}
