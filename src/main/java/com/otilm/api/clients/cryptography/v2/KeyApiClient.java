package com.otilm.api.clients.cryptography.v2;

import com.otilm.api.clients.ApiClientConnectorInfo;
import com.otilm.api.clients.BaseApiClient;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.interfaces.client.v2.KeySyncApiClient;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.cryptography.v2.OperationResponseValidator;
import com.otilm.api.model.connector.cryptography.v2.OperationValidationResult;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyAttributesRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.DestroyKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyDestructionStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationStatusResponseV2Dto;
import java.util.List;
import javax.net.ssl.TrustManager;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient implementation of the connector-facing stateless cryptography v2 key API.
 */
@SuppressWarnings("java:S1075")
public class KeyApiClient extends BaseApiClient implements KeySyncApiClient {

    private static final String BASE_PATH = "/v2/cryptographyProvider/keys";
    private static final String CREATE_ATTRIBUTES_PATH = BASE_PATH + "/create/attributes";
    private static final String CREATE_STATUS_PATH = BASE_PATH + "/create/status";
    private static final String CREATE_CANCEL_PATH = BASE_PATH + "/create/cancel";
    private static final String DESTROY_PATH = BASE_PATH + "/destroy";
    private static final String DESTROY_STATUS_PATH = BASE_PATH + "/destroy/status";
    private static final String DESTROY_CANCEL_PATH = BASE_PATH + "/destroy/cancel";
    private final OperationResponseValidator responseValidator;

    public KeyApiClient(WebClient webClient, TrustManager[] defaultTrustManagers,
            OperationResponseValidator responseValidator) {
        super(webClient, defaultTrustManagers);
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
            CreateKeyAttributesRequestV2Dto body) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        List<BaseAttribute> response = processRequest(r -> requireBody(r
                .uri(connector.getUrl() + CREATE_ATTRIBUTES_PATH)
                .bodyValue(body)
                .retrieve()
                .toEntityList(BaseAttribute.class), "listCreateKeyAttributes"), request, connector);
        requireValid(responseValidator.validateAttributeList(response), connector);
        return response;
    }

    @Override
    public ResponseEntity<KeyCreationResponseV2Dto> createKey(ApiClientConnectorInfo connector,
            CreateKeyRequestV2Dto body) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        ResponseEntity<KeyCreationResponseV2Dto> response = processRequest(r -> requireResponse(r
                .uri(connector.getUrl() + BASE_PATH)
                .bodyValue(body)
                .retrieve()
                .toEntity(KeyCreationResponseV2Dto.class), "createKey"), request, connector);
        requireValid(responseValidator.validateCreateKey(body.getExecutionMode(), response), connector);
        return response;
    }

    @Override
    public KeyCreationStatusResponseV2Dto getCreateKeyStatus(ApiClientConnectorInfo connector,
            KeyOperationRequestV2Dto body) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        KeyCreationStatusResponseV2Dto response = processRequest(r -> requireBody(r
                .uri(connector.getUrl() + CREATE_STATUS_PATH)
                .bodyValue(body)
                .retrieve()
                .toEntity(KeyCreationStatusResponseV2Dto.class), "getCreateKeyStatus"), request, connector);
        requireValid(responseValidator.validateCreateKeyStatus(response), connector);
        return response;
    }

    @Override
    public ResponseEntity<Void> cancelCreateKey(ApiClientConnectorInfo connector, KeyOperationRequestV2Dto body)
            throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        return processRequest(r -> requireResponse(
                r.uri(connector.getUrl() + CREATE_CANCEL_PATH).bodyValue(body).retrieve().toBodilessEntity(),
                "cancelCreateKey"), request, connector);
    }

    @Override
    public ResponseEntity<KeyOperationResponseV2Dto> destroyKey(ApiClientConnectorInfo connector,
            DestroyKeyRequestV2Dto body) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        ResponseEntity<KeyOperationResponseV2Dto> response = processRequest(r -> requireResponse(r
                .uri(connector.getUrl() + DESTROY_PATH)
                .bodyValue(body)
                .retrieve()
                .toEntity(KeyOperationResponseV2Dto.class), "destroyKey"), request, connector);
        requireValid(responseValidator.validateDestroy(body.getExecutionMode(), response), connector);
        return response;
    }

    @Override
    public KeyOperationStatusResponseV2Dto getDestroyKeyStatus(ApiClientConnectorInfo connector,
            KeyOperationRequestV2Dto body) throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        KeyDestructionStatusResponseV2Dto response = processRequest(r -> requireBody(r
                .uri(connector.getUrl() + DESTROY_STATUS_PATH)
                .bodyValue(body)
                .retrieve()
                .toEntity(KeyDestructionStatusResponseV2Dto.class), "getDestroyKeyStatus"), request, connector);
        requireValid(responseValidator.validateDestroyKeyStatus(response), connector);
        return response;
    }

    @Override
    public ResponseEntity<Void> cancelDestroyKey(ApiClientConnectorInfo connector, KeyOperationRequestV2Dto body)
            throws ConnectorException {
        WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST, connector, true);
        return processRequest(r -> requireResponse(
                r.uri(connector.getUrl() + DESTROY_CANCEL_PATH).bodyValue(body).retrieve().toBodilessEntity(),
                "cancelDestroyKey"), request, connector);
    }
}
