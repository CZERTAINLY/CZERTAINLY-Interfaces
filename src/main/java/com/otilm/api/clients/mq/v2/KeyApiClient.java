package com.otilm.api.clients.mq.v2;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.clients.mq.ProxyClient;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.interfaces.client.v2.KeySyncApiClient;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.cryptography.v2.OperationResponseValidator;
import com.otilm.api.model.connector.cryptography.v2.OperationTrackingRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.OperationValidationResult;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyAttributesRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.DestroyKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyDestructionStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * MQ implementation of the connector-facing stateless cryptography v2 key API.
 */
@SuppressWarnings("java:S1075")
public class KeyApiClient implements KeySyncApiClient {

    private static final String BASE_PATH = "/v2/cryptographyProvider/keys";
    private static final String CREATE_ATTRIBUTES_PATH = BASE_PATH + "/create/attributes";
    private static final String CREATE_STATUS_PATH = BASE_PATH + "/create/status";
    private static final String CREATE_CANCEL_PATH = BASE_PATH + "/create/cancel";
    private static final String DESTROY_PATH = BASE_PATH + "/destroy";
    private static final String DESTROY_STATUS_PATH = BASE_PATH + "/destroy/status";
    private static final String DESTROY_CANCEL_PATH = BASE_PATH + "/destroy/cancel";
    private static final String POST = "POST";
    private final ProxyClient proxyClient;
    private final OperationResponseValidator responseValidator;

    public KeyApiClient(ProxyClient proxyClient, OperationResponseValidator responseValidator) {
        this.proxyClient = proxyClient;
        this.responseValidator = responseValidator;
    }

    private static void requireValid(OperationValidationResult validation, ApiClientConnectorInfo connector)
            throws ConnectorException {
        if (!validation.isValid()) {
            IllegalArgumentException cause = validation.getCause();
            throw new ConnectorException(cause.getMessage(), cause, connector);
        }
    }

    @Override
    public List<BaseAttribute> listCreateKeyAttributes(ApiClientConnectorInfo connector,
            CreateKeyAttributesRequestV2Dto request) throws ConnectorException {
        BaseAttribute[] response = send(connector, CREATE_ATTRIBUTES_PATH, request, BaseAttribute[].class);
        if (response == null) {
            throw new ConnectorException("Connector returned an empty key-attributes response", connector);
        }
        List<BaseAttribute> attributes = Arrays.asList(response);
        requireValid(responseValidator.validateAttributeList(attributes), connector);
        return attributes;
    }

    @Override
    public ResponseEntity<KeyCreationResponseV2Dto> createKey(ApiClientConnectorInfo connector,
            CreateKeyRequestV2Dto request) throws ConnectorException {
        ResponseEntity<KeyCreationResponseV2Dto> response = sendEntity(connector, BASE_PATH, request,
                KeyCreationResponseV2Dto.class);
        requireValid(responseValidator.validateCreateKey(request, response), connector);
        return response;
    }

    @Override
    public KeyCreationStatusResponseV2Dto getCreateKeyStatus(ApiClientConnectorInfo connector,
            OperationTrackingRequestV2Dto request) throws ConnectorException {
        KeyCreationStatusResponseV2Dto response = send(connector, CREATE_STATUS_PATH, request,
                KeyCreationStatusResponseV2Dto.class);
        requireValid(responseValidator.validateCreateKeyStatus(response), connector);
        return response;
    }

    @Override
    public ResponseEntity<Void> cancelCreateKey(ApiClientConnectorInfo connector, OperationTrackingRequestV2Dto request)
            throws ConnectorException {
        return sendEntity(connector, CREATE_CANCEL_PATH, request, Void.class);
    }

    @Override
    public ResponseEntity<KeyOperationResponseV2Dto> destroyKey(ApiClientConnectorInfo connector,
            DestroyKeyRequestV2Dto request) throws ConnectorException {
        ResponseEntity<KeyOperationResponseV2Dto> response = sendEntity(connector, DESTROY_PATH, request,
                KeyOperationResponseV2Dto.class);
        requireValid(responseValidator.validateDestroy(request.getExecutionMode(), response), connector);
        return response;
    }

    @Override
    public KeyDestructionStatusResponseV2Dto getDestroyKeyStatus(ApiClientConnectorInfo connector,
            OperationTrackingRequestV2Dto request) throws ConnectorException {
        KeyDestructionStatusResponseV2Dto response = send(connector, DESTROY_STATUS_PATH, request,
                KeyDestructionStatusResponseV2Dto.class);
        requireValid(responseValidator.validateDestroyKeyStatus(response), connector);
        return response;
    }

    @Override
    public ResponseEntity<Void> cancelDestroyKey(ApiClientConnectorInfo connector,
            OperationTrackingRequestV2Dto request) throws ConnectorException {
        return sendEntity(connector, DESTROY_CANCEL_PATH, request, Void.class);
    }

    private <T> T send(ApiClientConnectorInfo connector, String path, Object body, Class<T> responseType)
            throws ConnectorException {
        try {
            return proxyClient.sendRequest(connector, path, POST, body, responseType);
        } catch (RuntimeException e) {
            throw new ConnectorException("Key request failed for " + path, e, connector);
        }
    }

    private <T> ResponseEntity<T> sendEntity(ApiClientConnectorInfo connector, String path, Object body,
            Class<T> responseType) throws ConnectorException {
        try {
            return proxyClient.sendRequestForEntity(connector, path, POST, body, responseType);
        } catch (RuntimeException e) {
            throw new ConnectorException("Key request failed for " + path, e, connector);
        }
    }
}
